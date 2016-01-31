package cz.muni.fi.pv256.movio.uco325253;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.db.FilmLoader;
import cz.muni.fi.pv256.movio.uco325253.model.Film;
import cz.muni.fi.pv256.movio.uco325253.sync.UpdaterSyncAdapter;

/**
 * This fragment displays a list of films.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Film>> {

    private static final String TAG = FilmListFragment.class.getSimpleName();

    @SuppressWarnings("FieldCanBeLocal")
    private StickyGridHeadersGridView mGvwMovies;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewStub mEmptyView;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView mTvwEmpty;
    @SuppressWarnings("FieldCanBeLocal")
    private BroadcastReceiver mBroadcastReceiver;

    private List<Film> mFavorites = null;
    private boolean mFavoritesRequested = false;

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

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {

                    case LoadService.INTENT_ACTION_LIST:
                        mGvwMovies.setAdapter(new FilmAdapter(getActivity(), DataLoader.getInstance().getFilms(), R.layout.list_item_film_header, R.layout.item_film));
                        break;

                    case UpdaterSyncAdapter.INTENT_ACTION_RESTART_LOADER:
                        getLoaderManager().restartLoader(0, null, FilmListFragment.this);
                        break;
                }
            }
        };

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver, new IntentFilter(LoadService.INTENT_ACTION_LIST));
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBroadcastReceiver, new IntentFilter(UpdaterSyncAdapter.INTENT_ACTION_RESTART_LOADER));
    }

    @Override
    public void onStart() {
        L.d(TAG, "onStart() called");

        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        mFavoritesRequested = ((MainActivity) getActivity()).isFavouritesRequested();

        if (getLoaderManager().getLoader(0) == null) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    /**
     * Either marks the favorites as being loaded or publishes them to the UI.
     */
    public void loadFavoriteFilms() {
        if (null == mFavorites) {
            mFavoritesRequested = true;
        } else {
            publishFavorites();
        }
    }

    /**
     * Loads Discover films into the UI.
     */
    public void loadDiscoverFilms() {
        mFavoritesRequested = false;
        if (DataLoader.getInstance().hasData()) {
            mGvwMovies.setAdapter(new FilmAdapter(getActivity(), DataLoader.getInstance().getFilms(), R.layout.list_item_film_header, R.layout.item_film));
        } else {
            Intent intent = new Intent(getActivity(), LoadService.class);
            intent.putExtra(LoadService.EXTRAS_KEY_LOAD, LoadService.EXTRAS_VALUE_LIST);
            getActivity().startService(intent);
        }
    }

    private void publishFavorites() {
        mGvwMovies.setAdapter(new FilmAdapter(getActivity(), mFavorites, R.layout.list_item_film_header, R.layout.item_film));
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public Loader<List<Film>> onCreateLoader(int id, Bundle args) {
        L.d(TAG, "onCreateLoader() called");
        return new FilmLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Film>> loader, List<Film> data) {
        L.d(TAG, "onLoadFinished() called");
        mFavorites = data;

        if (mFavoritesRequested) {
            publishFavorites();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Film>> loader) {
        L.d(TAG, "onLoaderReset() called");

        if (mFavoritesRequested) {
            publishFavorites();
        }
    }

}
