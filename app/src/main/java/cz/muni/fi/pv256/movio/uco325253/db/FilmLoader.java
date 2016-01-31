package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.L;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This class serves as the data loader for the get-all query.
 * <p/>
 * Created by xosvald on 30.01.2016.
 */
public class FilmLoader extends AsyncTaskLoader<List<Film>> {

    private static final String TAG = FilmLoader.class.getSimpleName();

    private FilmManager mFilmManager;
    private List<Film> mData;

    /**
     * Parametric constructor. Initializes the loader with given Android context.
     *
     * @param context Android context to initialize the loader with
     */
    public FilmLoader(Context context) {
        super(context);
        mFilmManager = new FilmManager(context);
    }

    @Override
    public List<Film> loadInBackground() {
        Log.d(TAG, "loadInBackground() called");
        mData = mFilmManager.getAll();
        return mData;
    }

    @Override
    protected void onStartLoading() {
        L.d(TAG, "onStartLoading called()");
        super.onStartLoading();

        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onReset() {
        Log.d(TAG, "onReset");
        super.onReset();

        mData = null;
    }

    @Override
    public void deliverResult(List<Film> data) {
        L.d(TAG, String.format("deliverResult() called, data: %s", data));

        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }
    }

}
