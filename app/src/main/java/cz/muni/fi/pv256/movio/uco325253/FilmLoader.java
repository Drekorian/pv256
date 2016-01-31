package cz.muni.fi.pv256.movio.uco325253;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * TODO: proper implemntation
 * Created by xosvald on 30.01.2016.
 */
public class FilmLoader extends AsyncTaskLoader<List<Film>> {

    /**
     * Parametric constructor. Initializes the loader with given Android context.
     *
     * @param context Android context to intialize the loader with
     */
    public FilmLoader(Context context) {
        super(context);
    }

    @Override
    public List<Film> loadInBackground() {
        // TODO: implement ME!
        return null;
    }

    @Override
    public void deliverResult(List<Film> data) {
        super.deliverResult(data);
    }

}
