package cz.muni.fi.pv256.movio.uco325253;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import cz.muni.fi.pv256.movio.uco325253.db.FilmManager;
import cz.muni.fi.pv256.movio.uco325253.model.Cast;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This fragment displays the detailed information about a movie.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmDetailFragment extends Fragment {

    private static final String TAG = FilmDetailFragment.class.getSimpleName();

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
    private FloatingActionButton mFab;
    private Picasso mPicasso;

    // TODO pass via argument passing
    private boolean mFavorite;

    private BroadcastReceiver mBroadcastReceiver;
    private FilmManager mFilmManager;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mPicasso = Picasso.with(getActivity());

        if (BuildConfig.DEBUG) {
            mPicasso.setIndicatorsEnabled(true);
        }

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processFilmDetails(inflater);
            }
        };

        mFilmManager = new FilmManager(getActivity());

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
    public void setFilm(final Film film) {
        L.d(TAG, "setFilm() called, film: " + film);

        if (null != film) {
            mFilm = film;
            mTitle.setText(mFilm.getTitle());
            mRoot.setVisibility(null != mFilm ? View.VISIBLE : View.INVISIBLE);
            mOverview.setText(mFilm.getOverview());
            mYear.setText(String.valueOf(mFilm.formatReleaseDate()));

            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mFavorite) {
                        mFavorite = true;
                        mFab.setImageResource(R.drawable.ic_action_minus);
                        mFilmManager.add(mFilm);
                    } else {
                        mFavorite = false;
                        mFab.setImageResource(R.drawable.ic_action_add);
                        mFilmManager.delete(mFilm);
                    }
                }
            });

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

                LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver, new IntentFilter(LoadService.INTENT_ACTION_DETAIL));
                Intent intent = new Intent(getActivity(), LoadService.class);
                intent.putExtra(LoadService.EXTRAS_KEY_LOAD, LoadService.EXTRAS_VALUE_DETAILS);
                intent.putExtra(LoadService.EXTRAS_KEY_ID, mFilm.getId());
                getActivity().startService(intent);
            } else {
                L.d(TAG, "Director and cast cached.");
                processFilmDetails(LayoutInflater.from(getActivity()));
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        L.d(TAG, "onSaveInstanceState() called, outState: " + outState);
        outState.putParcelable(SAVE_STATE_KEY_FILM, mFilm);
    }

    private void processFilmDetails(@NonNull LayoutInflater inflater) {
        mDirector.setText(DataLoader.getInstance().getDirector(mFilm.getId()));

        final Transformation transformation = new CircleTransformation();

        for (Cast cast : DataLoader.getInstance().getCast(mFilm.getId())) {
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
    }

}
