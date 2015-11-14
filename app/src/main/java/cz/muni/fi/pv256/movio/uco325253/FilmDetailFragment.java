package cz.muni.fi.pv256.movio.uco325253;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;

import cz.muni.fi.pv256.movio.uco325253.model.Cast;
import cz.muni.fi.pv256.movio.uco325253.model.CastWrapper;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This fragment displays the detailed information about a movie.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmDetailFragment extends Fragment {

    private static final String TAG = FilmDetailFragment.class.getSimpleName();

    private static final String DEPARTMENT_DIRECTING = "Directing";
    private static final String SAVE_STATE_KEY_FILM = "film";

    private View mRoot;
    private TextView mTitle;
    private TextView mYear;
    private TextView mDirector;
    private ImageView mBackdrop;
    private ImageView mPoster;
    private TextView mOverview;
    private TableLayout mCast;
    private Film mFilm;
    private Picasso mPicasso;

    private LoadTask mLoadTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        L.d(TAG, "onCreateView() called, inflater: " + inflater + ", container: " + container + ", savedInstanceState: " + savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_film_detail, container, false);

        mRoot = view;
        mTitle = (TextView) view.findViewById(R.id.tvwTitle);
        mYear = (TextView) view.findViewById(R.id.tvwYear);
        mDirector = (TextView) view.findViewById(R.id.tvwDirector);
        mBackdrop = (ImageView) view.findViewById(R.id.ivwBackdrop);
        mPoster = (ImageView) view.findViewById(R.id.ivwPoster);
        mOverview = (TextView) view.findViewById(R.id.tvwOverview);
        mCast = (TableLayout) view.findViewById(R.id.tltCast);
        mPicasso = Picasso.with(getActivity());

        if (BuildConfig.DEBUG) {
            mPicasso.setIndicatorsEnabled(true);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        L.d(TAG, "onViewCreated() called, view: " + view + ", savedInstanceState: " + savedInstanceState);

        super.onViewCreated(view, savedInstanceState);

        if (null != savedInstanceState) {
            setFilm((Film) savedInstanceState.getParcelable(SAVE_STATE_KEY_FILM));
        }

        mRoot.setVisibility(null != mFilm ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Sets film associated with the fragment.
     *
     * @param film film to be set
     */
    public void setFilm(Film film) {
        L.d(TAG, "setFilm() called, film: " + film);

        if (null != film) {
            mFilm = film;
            mTitle.setText(mFilm.getTitle());
            mRoot.setVisibility(null != mFilm ? View.VISIBLE : View.INVISIBLE);
            mOverview.setText(mFilm.getOverview());
            mYear.setText(String.valueOf(mFilm.getReleaseYear()));

            mPicasso.load(TheMovieDB.API_IMAGES_BASE_URL + film.getPosterPath())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.film_placeholder)
                    .into(mPoster);

            mPicasso.load(TheMovieDB.API_IMAGES_BASE_URL + film.getBackdropPath())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.film_placeholder)
                    .into(mBackdrop);

            if (!DataLoader.getInstance().hasDirectorAndCast(mFilm.getId())) {
                L.d(TAG, "Loading director and cast for " + mFilm.getTitle() + ".");

                if (null != mLoadTask) {
                    mLoadTask.cancel(true);
                }

                mLoadTask = new LoadTask(mFilm.getId());
                mLoadTask.execute();
            } else {
                L.d(TAG, "Director and cast cached.");
            }
        }
    }

    @Override
    public void onStop() {
        L.d(TAG, "onStop()");

        super.onStop();

        if (null != mLoadTask) {
            mLoadTask.cancel(true);
            mLoadTask = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        L.d(TAG, "onSaveInstanceState() called, outState: " + outState);
        outState.putParcelable(SAVE_STATE_KEY_FILM, mFilm);
    }

    private class LoadTask extends AsyncTask<Void, Void, Boolean> {

        private Gson mGson;
        private long mFilmID;

        /**
         * Parametric constructor. Sets film ID.
         *
         * @param filmID unique film ID to be set
         */
        public LoadTask(long filmID) {
            mGson = new Gson();
            mFilmID = filmID;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final Response response = DataLoader.getInstance().loadCast(mFilmID);

            if (null != response && response.isSuccessful()) {
                try {
                    CastWrapper entireCast = mGson.fromJson(response.body().charStream(), CastWrapper.class);

                    DataLoader.getInstance().addCast(mFilmID, entireCast.getCast());

                    for (Cast cast : entireCast.getCrew()) {
                        if (DEPARTMENT_DIRECTING.equals(cast.getDepartment())) {
                            DataLoader.getInstance().addDirector(mFilmID, cast.getName());
                            break;
                        }
                    }

                    response.body().close();
                    return Boolean.TRUE;

                } catch (IOException ex) {
                    L.e(TAG, "Unable to parse cast, original exception " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
                    ex.printStackTrace();
                }
            }

            return Boolean.FALSE;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            if (null != result) {
                if (result) {
                    mDirector.setText(DataLoader.getInstance().getDirector(mFilmID));

                    final LayoutInflater inflater = LayoutInflater.from(getActivity());
                    final Transformation transformation = new CircleTransformation();

                    for (Cast cast : DataLoader.getInstance().getCast(mFilmID)) {
                        View view = inflater.inflate(R.layout.item_cast, mCast, false);
                        ImageView ivwCast = (ImageView) view.findViewById(R.id.ivwCast);
                        TextView tvwCast = (TextView) view.findViewById(R.id.tvwCast);

                        tvwCast.setText(cast.getName());
                        mCast.addView(view);

                        if (null != cast.getProfilePath()) {
                            L.d(TAG, "Loading cast photo for " + cast.getName() + ".");
                            mPicasso.load(TheMovieDB.API_IMAGES_BASE_URL + cast.getProfilePath())
                                    .fit()
                                    .centerCrop()
                                    .transform(transformation)
                                    .into(ivwCast);
                        } else {
                            L.i(TAG, "Cast photo for " + cast.getName() + " is null.");
                        }
                    }
                } else {
                    L.e(TAG, "Unable to publish the load result, result not successful.");
                }
            } else {
                L.e(TAG, "Unable to publish the load result, result is null.");
            }
        }

    }

}
