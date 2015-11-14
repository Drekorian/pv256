package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.io.IOException;
import java.util.Arrays;

import cz.muni.fi.pv256.movio.uco325253.model.Film;
import cz.muni.fi.pv256.movio.uco325253.model.ResultWrapper;

/**
 * This fragment displays a list of films.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmListFragment extends Fragment {

    private static final String TAG = FilmListFragment.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    private StickyGridHeadersGridView mGvwMovies;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewStub mEmptyView;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView mTvwEmpty;

    private LoadTask mLoadTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(TAG, "onCreateView() called, inflater: " + inflater + ", container: " + container + ", savedInstanceState: " + savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_film_list, container, false);

        mGvwMovies = (StickyGridHeadersGridView) view.findViewById(android.R.id.list);
        mEmptyView = (ViewStub) view.findViewById(android.R.id.empty);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "onViewCreated() called, view: " + view + ", savedInstanceState: " + savedInstanceState);

        super.onViewCreated(view, savedInstanceState);
        mGvwMovies.setEmptyView(mEmptyView);

        mTvwEmpty = (TextView) view.findViewById(R.id.tvwEmpty);

        if (null != mTvwEmpty) {
            mTvwEmpty.setText(isNetworkAvailable() ? R.string.no_data : R.string.no_connection);
        }

        final AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FilmAdapter.FilmViewHolder viewHolder = (FilmAdapter.FilmViewHolder) view.getTag();

                if (View.VISIBLE == viewHolder.textView.getVisibility()) {
                    viewHolder.textView.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.textView.setVisibility(View.VISIBLE);
                }

                return true;
            }
        };

        mGvwMovies.setOnItemLongClickListener(longClickListener);
        mGvwMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Film film = ((FilmAdapter.FilmViewHolder) view.getTag()).film;
                getMainActivity().displayFilmDetail(film);
            }

        });
    }

    @Override
    public void onStart() {
        L.d(TAG, "onStart() called");

        super.onStart();

        if (DataLoader.getInstance().hasData()) {
            mGvwMovies.setAdapter(new FilmAdapter(getActivity(), DataLoader.getInstance().getFilms(), R.layout.list_item_film_header, R.layout.item_film));
        } else {
            if (null != mLoadTask) {
                mLoadTask.cancel(true);
            }

            mLoadTask = new LoadTask();
            mLoadTask.execute();
        }
    }

    @Override
    public void onStop() {
        L.d(TAG, "onStop() called");

        super.onStop();

        if (null != mLoadTask) {
            mLoadTask.cancel(true);
            mLoadTask = null;
        }
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        L.d("", "" + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class LoadTask extends AsyncTask<Void, Void, Boolean> {

        private Gson mGson;

        /**
         * Default constructor. Sets up the GSON parser.
         */
        public LoadTask() {
            mGson = new Gson();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final DataLoader dataLoader = DataLoader.getInstance();

            Response response = dataLoader.loadUpcomingFilms();

            if (null != response) {
                if (response.isSuccessful()) {
                    L.d(TAG, "ResultWrapper successful, response code: " + response.code());

                    try {
                        final ResultWrapper resultWrapper = mGson.fromJson(response.body().charStream(), ResultWrapper.class);
                        response.body().close();

                        addSection(resultWrapper.getResults(), "Upcoming films");
                        DataLoader.getInstance().addFilms(Arrays.asList(resultWrapper.getResults()));
                    } catch (IOException ex) {
                        L.e(TAG, "Data loading has failed: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                        ex.printStackTrace();
                    }
                } else {
                    L.e(TAG, "Unable to loadUpcomingFilms data, response code: " + response.code() + ", response message: " + response.message());
                    // handle error
                }
            } else {
                L.e(TAG, "Unable to load Upcoming Films data, response is null");
            }

            response = dataLoader.loadInTheatersFilms();

            if (null != response) {
                if (response.isSuccessful()) {
                    L.d(TAG, "ResultWrapper successful, response code: " + response.code());

                    try {
                        final ResultWrapper resultWrapper = mGson.fromJson(response.body().charStream(), ResultWrapper.class);
                        response.body().close();

                        addSection(resultWrapper.getResults(), "In Theaters");
                        DataLoader.getInstance().addFilms(Arrays.asList(resultWrapper.getResults()));
                        return Boolean.TRUE;
                    } catch (IOException ex) {
                        L.e(TAG, "Data loading has failed: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                        ex.printStackTrace();
                    }

                } else {
                    L.e(TAG, "Unable to load In Theaters data, response code: " + response.code() + ", response message: " + response.message());
                }
            } else {
                L.e(TAG, "Unable to load In Theaters data, response is null");
            }

            return Boolean.FALSE;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (null != result && result) {
                mGvwMovies.setAdapter(new FilmAdapter(getActivity(), DataLoader.getInstance().getFilms(), R.layout.list_item_film_header, R.layout.item_film));
            }
        }

        private void addSection(Film[] films, String section) {
            for (Film film : films) {
                film.setSection(section);
            }
        }

    }

}
