package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.R;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

import static cz.muni.fi.pv256.movio.uco325253.db.FilmContract.FilmEntry;

/**
 * This class serves as the database entity manager for the Film entity.
 * <p/>
 * Created by xosvald on 04.12.2015.
 */
public class FilmManager {

    public static final int COLUMN_FILM_ID = 0;
    public static final int COLUMN_FILM_TITLE = 1;
    public static final int COLUMN_FILM_OVERVIEW = 2;
    public static final int COLUMN_FILM_RELEASE_DATE = 3;
    public static final int COLUMN_FILM_POSTER_PATH = 4;
    public static final int COLUMN_FILM_BACKDROP_PATH = 5;

    private static final String[] FILM_COLUMNS = {
            FilmEntry._ID,
            FilmEntry.COLUMN_TITLE,
            FilmEntry.COLUMN_OVERVIEW,
            FilmEntry.COLUMN_RELEASE_DATE,
            FilmEntry.COLUMN_POSTER_PATH,
            FilmEntry.COLUMN_BACKDROP_PATH
    };

    /**
     * Where string with selection based on film unique ID
     */
    private static final String WHERE_ID = FilmEntry._ID + " = ?";

    private Context mContext;

    /**
     * Parametric constructor. Initializes the manager with given Android context.
     *
     * @param context Android context to initialize the manager with
     */
    public FilmManager(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Returns a list of all stored films.
     *
     * @return a list of all stored films
     */
    public List<Film> getAll() {
        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, FILM_COLUMNS, null, null, null);

        if (null != cursor && cursor.moveToFirst()) {
            List<Film> films = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    films.add(getFilm(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }

            return films;
        }

        // default result is empty collection
        return Collections.emptyList();
    }

    public Film find(long id) {
        Cursor cursor = mContext.getContentResolver().query(FilmEntry.CONTENT_URI, FILM_COLUMNS, WHERE_ID, new String[]{String.valueOf(id)}, null);

        if (null != cursor && cursor.moveToFirst()) {
            try {
                if (1 == cursor.getCount()) {
                    return getFilm(cursor);
                }
            } finally {
                cursor.close();
            }
        }

        // default result null
        return null;
    }

    /**
     * Inserts a film into the database.
     *
     * @param film film to be inserted
     */
    public void add(Film film) {
        if (null == film) {
            throw new IllegalArgumentException("Film is null.");
        }

        if (null == film.getTitle()) {
            throw new IllegalArgumentException("Film title must not be null.");
        }

        mContext.getContentResolver().insert(FilmEntry.CONTENT_URI, prepareFilmValues(film));
    }

    /**
     * Updates a film in the database.
     *
     * @param film film to update in the database
     */
    @SuppressWarnings("unused")
    public void update(Film film) {
        if (null == film) {
            throw new IllegalArgumentException("Film is null.");
        }

        if (null == film.getTitle()) {
            throw new IllegalArgumentException("Film title must not be null.");
        }

        mContext.getContentResolver().update(FilmEntry.CONTENT_URI, prepareFilmValues(film), WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    /**
     * Removes a film from the database.
     *
     * @param film film to remove from the database.
     */
    public void delete(Film film) {
        if (null == film) {
            throw new IllegalArgumentException("Film is null");
        }

        mContext.getContentResolver().delete(FilmEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(film.getId())});
    }

    /**
     * Puts film attribute values into a content values for query.
     *
     * @param film film to put the values into a query
     * @return content values with film attribute values
     */
    private ContentValues prepareFilmValues(Film film) {
        ContentValues values = new ContentValues();
        values.put(FilmEntry._ID, film.getId());
        values.put(FilmEntry.COLUMN_TITLE, film.getTitle());
        values.put(FilmEntry.COLUMN_OVERVIEW, film.getOverview());
        values.put(FilmEntry.COLUMN_RELEASE_DATE, film.getReleaseDate());
        values.put(FilmEntry.COLUMN_POSTER_PATH, film.getPosterPath());
        values.put(FilmEntry.COLUMN_BACKDROP_PATH, film.getBackdropPath());
        return values;
    }

    /**
     * Loads film values from the given cursor.
     *
     * @param cursor cursor to load the film data from
     * @return an instance of film with the loaded data
     */
    private Film getFilm(Cursor cursor) {
        Film film = new Film();
        film.setId(cursor.getLong(COLUMN_FILM_ID));
        film.setTitle(cursor.getString(COLUMN_FILM_TITLE));
        film.setOverview(cursor.getString(COLUMN_FILM_OVERVIEW));
        film.setReleaseDate(cursor.getString(COLUMN_FILM_RELEASE_DATE));
        film.setPosterPath(cursor.getString(COLUMN_FILM_POSTER_PATH));
        film.setBackdropPath(cursor.getString(COLUMN_FILM_BACKDROP_PATH));
        film.setSection(mContext.getString(R.string.section_favorites));
        return film;
    }

}
