package cz.muni.fi.pv256.movio.uco325253.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.L;
import cz.muni.fi.pv256.movio.uco325253.R;
import cz.muni.fi.pv256.movio.uco325253.TheMovieDB;
import cz.muni.fi.pv256.movio.uco325253.TheMovieDBAPI;
import cz.muni.fi.pv256.movio.uco325253.db.FilmManager;
import cz.muni.fi.pv256.movio.uco325253.model.Film;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * This class serves as the sync adapter.
 * <p/>
 * Created by xosvald on 31.01.2016.
 */
public class UpdaterSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = UpdaterSyncService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 0;
    private static final int NOTIFICATION_ERROR_ID = 1;

    public static final String INTENT_ACTION_RESTART_LOADER = "intentActionRestartLoader";

    /**
     * This interval describes the interval how often to sync the movie data.
     */
    public static final int SYNC_INTERVAL = 60 * 60 * 24; // 1 day
    /**
     * This interval describes the interval flexibility.
     */
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private Context mContext;
    private FilmManager mFilmManager;
    @SuppressWarnings("FieldCanBeLocal")
    private Retrofit mRetrofit;
    private TheMovieDBAPI mApi;
    @SuppressWarnings("FieldCanBeLocal")
    private boolean mFakeData;

    /**
     * Parametric constructor. Sets Android context and whether sync adapter should auto-initialize.
     *
     * @param context        Android context to set
     * @param autoInitialize true, provided that the sync adapter should auto-initialize.
     */
    public UpdaterSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContext = context;
        mFilmManager = new FilmManager(mContext);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDB.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mApi = mRetrofit.create(TheMovieDBAPI.class);
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        L.d(TAG, "configurePeriodicSync() called");

        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(Bundle.EMPTY) //enter non null Bundle, otherwise on some phones it crashes sync
                    .build();

            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);
        }
    }

    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        L.d(TAG, "syncImmediately() called");

        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        L.d(TAG, "initializeSyncAdapter() called");

        getSyncAccount(context);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one if the
     * fake account doesn't exist yet.  If we make a new account, we call the onAccountCreated
     * method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        L.d(TAG, "getSyncAccount() called");

        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(context.getString(R.string.sync_account_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.
             */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        L.d(TAG, "onAccountCreated() called");

        /*
         * Since we've created an account
         */
        UpdaterSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        L.d(TAG, "onPerformSync() called");

        // load data faking preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        mFakeData = sharedPreferences.getBoolean(TheMovieDB.SHARED_PREFERENCES_KEY_FAKE_DATA, false);

        List<Film> films = mFilmManager.getAll();
        final int size = films.size();

        boolean newData = false;

        for (int i = 0; i < size; i++) {
            final Film film = films.get(i);
            L.d(TAG, String.format("Loading update data for: %s", film.getTitle()));

            Call<Film> detailsCall = mApi.loadMovieDetails(films.get(i).getId(), TheMovieDB.API_KEY);

            try {
                Response response = detailsCall.execute();

                if (null != response) {
                    Log.d(TAG, response.body().toString());
                    if (response.isSuccess()) {
                        Film filmUpdate = (Film) response.body();

                        if (mFakeData) {
                            L.d(TAG, "Faking data update");
                            filmUpdate.setTitle(filmUpdate.getTitle() + " (fake)");
                        }

                        boolean change = checkFilm(films.get(i), filmUpdate);

                        if (change) {
                            L.d(TAG, String.format("Change in %s detected", film.getTitle()));
                            mFilmManager.update(filmUpdate);
                        }

                        newData |= change;
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
        }

        if (newData) {
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            Notification notification = new NotificationCompat.Builder(mContext)
                    .setSmallIcon(R.drawable.ic_action_add)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_account))
                    .setContentTitle(mContext.getString(R.string.synchronization_complete))
                    .setContentText(mContext.getString(R.string.new_info_found))
                    .build();

            notificationManager.notify(NOTIFICATION_ID, notification);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(new Intent(INTENT_ACTION_RESTART_LOADER));
        }
    }

    private boolean checkFilm(Film oldFilm, Film newFilm) {
        boolean titlesEqual = oldFilm.getTitle().equals(newFilm.getTitle());
        boolean overviewEqual = null == oldFilm.getOverview() ? newFilm.getOverview() == null : oldFilm.getOverview().equals(newFilm.getOverview());
        boolean releaseDateEqual = null == oldFilm.getReleaseDate() ? newFilm.getReleaseDate() == null : oldFilm.getReleaseDate().equals(newFilm.getReleaseDate());
        boolean posterPathEqual = null == oldFilm.getPosterPath() ? newFilm.getPosterPath() == null : oldFilm.getPosterPath().equals(newFilm.getPosterPath());
        boolean backdropPathEqual = null == oldFilm.getBackdropPath() ? newFilm.getBackdropPath() == null : oldFilm.getBackdropPath().equals(newFilm.getBackdropPath());

        return !(titlesEqual && overviewEqual && releaseDateEqual && posterPathEqual && backdropPathEqual);
    }

    private void handleError() {
        Notification notification = new android.support.v7.app.NotificationCompat.Builder(mContext)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(mContext.getString(R.string.parse_error_title))
                .setContentText(mContext.getString(R.string.parse_error_text))
                .build();

        final NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ERROR_ID, notification);
    }

}
