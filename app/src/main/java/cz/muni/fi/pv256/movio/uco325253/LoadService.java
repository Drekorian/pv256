package cz.muni.fi.pv256.movio.uco325253;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.muni.fi.pv256.movio.uco325253.model.Cast;
import cz.muni.fi.pv256.movio.uco325253.model.CastWrapper;
import cz.muni.fi.pv256.movio.uco325253.model.Film;
import cz.muni.fi.pv256.movio.uco325253.model.ResultWrapper;
import retrofit.Call;
import retrofit.Converter;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static cz.muni.fi.pv256.movio.uco325253.DateUtils.aWeekAgoFrom;
import static cz.muni.fi.pv256.movio.uco325253.DateUtils.aWeekFrom;
import static cz.muni.fi.pv256.movio.uco325253.DateUtils.formatDate;
import static cz.muni.fi.pv256.movio.uco325253.DateUtils.tomorrow;

/**
 * This service loads the necessary data from the HTTP API.
 * <p/>
 * Created by xosvald on 14.11.2015.
 */
public class LoadService extends IntentService {

    private static final String TAG = LoadService.class.getSimpleName();

    private static final String NAME = LoadService.class.getSimpleName();

    private static final int ID = 0;

    /**
     * Intent action to display list of films.
     */
    public static final String INTENT_ACTION_LIST = "intentActionList";
    /**
     * Intent action to display film details.
     */
    public static final String INTENT_ACTION_DETAIL = "intentActionDetail";

    /**
     * Intent extras key load.
     */
    public static final String EXTRAS_KEY_LOAD = "load";
    /**
     * Intent extras key id.
     */
    public static final String EXTRAS_KEY_ID = "id";
    /**
     * Intent extras value list.
     */
    public static final String EXTRAS_VALUE_LIST = "list";
    /**
     * Intent extras value details.
     */
    public static final String EXTRAS_VALUE_DETAILS = "details";

    private static final String DEPARTMENT_DIRECTING = "Directing";

    private Retrofit mRetrofit;
    private TheMovieDBAPI mApi;
    private Gson mGson;

    /**
     * Creates an IntentService. Invoked by your subclass's constructor.
     */
    public LoadService() {
        super(NAME);
        mGson = new Gson();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        L.d(TAG, "onHandleIntent() called, intent: " + intent);

        final Bundle extras = intent.getExtras();

        if (null != extras) {
            final String load = extras.getString(EXTRAS_KEY_LOAD);

            if (null != load) {

                Intent broadcastIntent;
                initializeRetrofit(load);

                switch (load) {
                    case EXTRAS_VALUE_LIST:
                        Calendar now = new GregorianCalendar();
                        Call<Film[]> filmsCall = mApi.loadUpcomingFilms(TheMovieDB.API_KEY, TheMovieDB.QUERY_PARAM_VALUE_AVG_RATING_DESC, formatDate(tomorrow(now)), formatDate(aWeekFrom(now)));

                        try {
                            Response response = filmsCall.execute();

                            if (null != response) {
                                if (response.isSuccess()) {
                                    Film[] films = (Film[]) response.body();
                                    addSection(films, getString(R.string.upcoming_films));
                                    DataLoader.getInstance().addFilms(Arrays.asList(films));
                                } else {
                                    L.e(TAG, "Response unsuccessful, response: " + response.code() + ", message: " + response.message());
                                    handleError();
                                    return;
                                }
                            } else {
                                L.e(TAG, "Unable to return response, response is null.");
                                handleError();
                                return;
                            }

                        } catch (IOException ex) {
                            L.e(TAG, "Unable to get response: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                            ex.printStackTrace();
                            handleError();
                            return;
                        }

                        filmsCall = mApi.loadUpcomingFilms(TheMovieDB.API_KEY, TheMovieDB.QUERY_PARAM_VALUE_AVG_RATING_DESC, formatDate(aWeekAgoFrom(now)), formatDate(now));

                        try {
                            Response response = filmsCall.execute();

                            if (null != response) {
                                if (response.isSuccess()) {
                                    Film[] films = (Film[]) response.body();
                                    addSection(films, getString(R.string.in_theaters));
                                    DataLoader.getInstance().addFilms(Arrays.asList(films));
                                } else {
                                    L.e(TAG, "Response unsuccessful, response: " + response.code() + ", message: " + response.message());
                                    handleError();
                                    return;
                                }
                            } else {
                                L.e(TAG, "Unable to return response, response is null.");
                                handleError();
                                return;
                            }

                        } catch (IOException ex) {
                            L.e(TAG, "Unable to get response: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                            ex.printStackTrace();
                            handleError();
                            return;
                        }

                        broadcastIntent = new Intent(INTENT_ACTION_LIST);
                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
                        break;

                    case EXTRAS_VALUE_DETAILS:
                        if (!extras.containsKey(EXTRAS_KEY_ID)) {
                            L.e(TAG, "No ID provided.");
                            handleError();
                            return;
                        }

                        final long filmID = extras.getLong(EXTRAS_KEY_ID);
                        Call<CastWrapper> detailsCall = mApi.loadCastAndCrew(filmID, TheMovieDB.API_KEY);

                        try {
                            Response response = detailsCall.execute();

                            if (null != response) {
                                Log.d(TAG, response.body().toString());
                                if (response.isSuccess()) {
                                    CastWrapper entireCast = (CastWrapper) response.body();

                                    if (null != entireCast) {

                                        for (Cast cast : entireCast.getCrew()) {
                                            if (DEPARTMENT_DIRECTING.equals(cast.getDepartment())) {
                                                DataLoader.getInstance().addDirector(filmID, cast.getName());
                                                break;
                                            }
                                        }

                                        DataLoader.getInstance().addCast(filmID, Arrays.asList(entireCast.getCast()));
                                        broadcastIntent = new Intent(INTENT_ACTION_DETAIL);
                                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
                                    }
                                } else {
                                    L.e(TAG, "Response unsuccessful, response: " + response.code() + ", message: " + response.message());
                                    handleError();
                                    return;
                                }
                            } else {
                                L.e(TAG, "Unable to return response, response is null.");
                                handleError();
                                return;
                            }

                        } catch (IOException ex) {
                            L.e(TAG, "Unable to get response: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                            ex.printStackTrace();
                            handleError();
                        }

                        break;

                    default:
                        L.e(TAG, "Unknown load value: " + load);
                        break;
                }
            } else {
                L.e(TAG, "Invalid intent, extras key " + EXTRAS_KEY_LOAD + " not present.");
            }
        } else {
            L.e(TAG, "Invalid intent: extras are null.");
        }
    }

    private void initializeRetrofit(String action) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {
                L.d(TAG, chain.request().toString());

                return chain.proceed(chain.request());
            }
        });

        switch (action) {
            case EXTRAS_VALUE_LIST:
                mRetrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(TheMovieDB.API_BASE_URL)
                        .addConverterFactory(new Converter.Factory() {
                            @Override
                            public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
                                return new Converter<ResponseBody, Film[]>() {

                                    @Override
                                    public Film[] convert(ResponseBody value) throws IOException {
                                        final ResultWrapper resultWrapper = mGson.fromJson(value.charStream(), ResultWrapper.class);
                                        value.close();
                                        return resultWrapper.getResults();
                                    }

                                };
                            }
                        })
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;

            case EXTRAS_VALUE_DETAILS:
                mRetrofit = new Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl(TheMovieDB.API_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
        }

        mApi = mRetrofit.create(TheMovieDBAPI.class);
    }

    private void addSection(Film[] films, String section) {
        for (Film film : films) {
            film.setSection(section);
        }
    }

    private void handleError() {
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(getString(R.string.parse_error_title))
                .setContentText(getString(R.string.parse_error_text))
                .build();

        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID, notification);
    }

}
