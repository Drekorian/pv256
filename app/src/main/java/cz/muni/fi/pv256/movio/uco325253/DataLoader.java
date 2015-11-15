package cz.muni.fi.pv256.movio.uco325253;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.muni.fi.pv256.movio.uco325253.model.Cast;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This class loads movie data from the web API and stores them in a cache.
 * <p/>
 * Created by xosvald on 07.11.2015.
 */
public class DataLoader {

    private static final String TAG = DataLoader.class.getSimpleName();

    private static DataLoader sInstance;

    private List<Film> mFilms;
    private Map<Long, String> mDirectors;
    private Map<Long, List<Cast>> mCast;

    /**
     * Returns data loader singleton instance.
     *
     * @return data loader singleton instance
     */
    public static DataLoader getInstance() {
        L.d(TAG, "getInstance() called");

        if (null == sInstance) {
            sInstance = new DataLoader();
        }

        return sInstance;
    }

    /**
     * Default constructor. Private in order to ensure the singleton usage.
     */
    private DataLoader() {
        mDirectors = new HashMap<>();
        mCast = new HashMap<>();
    }

    /**
     * Returns list of cached films.
     *
     * @return list of cached films
     */
    public List<Film> getFilms() {
        L.d(TAG, "getFilms() called");
        return mFilms;
    }

    /**
     * Adds films to cache.
     *
     * @param films films to be added to the cache.
     */
    public void addFilms(List<Film> films) {
        L.d(TAG, "addFilms() called, films: " + films);

        if (null == mFilms) {
            mFilms = new ArrayList<>();
        }

        mFilms.addAll(films);
    }

    /**
     * Adds a director to the cache.
     *
     * @param filmID       unique film ID to retrieve the director for
     * @param directorName director name to be added to the cache
     */
    public void addDirector(long filmID, String directorName) {
        L.d(TAG, "addDirector() called, filmID: " + filmID + ", directorName: " + directorName);
        mDirectors.put(filmID, directorName);
    }

    /**
     * Adds film cast to the cache.
     *
     * @param filmID unique film ID to be added to the cache
     * @param cast   cast to be added to the cache
     */
    public void addCast(long filmID, List<Cast> cast) {
        L.d(TAG, "addCast() called, filmID: " + filmID + ", cast: " + cast);
        mCast.put(filmID, cast);
    }

    /**
     * Returns whether the cache has any loaded data.
     *
     * @return true, provided that the cache has any loaded data, false otherwise
     */
    public boolean hasData() {
        L.d(TAG, "hasData() called");
        return null != mFilms;
    }

    /**
     * Returns cached director name for the given film.
     *
     * @param filmID unique film ID to retrieve the director for
     * @return director name, provided that one is cached for given movie, null otherwise
     */
    public String getDirector(long filmID) {
        L.d(TAG, "getDirector() called, filmID: " + filmID);
        return mDirectors.get(filmID);
    }

    /**
     * Returns list of cached cast for given film.
     *
     * @param filmID unique film ID to retrieve the cast list for
     * @return list of cached cast for given film
     */
    public List<Cast> getCast(long filmID) {
        L.d(TAG, "getCast() called, filmID: " + filmID);
        return mCast.get(filmID);
    }

    /**
     * Returns whether the film cast and director have already been cached.
     *
     * @param filmID unique film ID for which the retrieved cache data availability
     * @return true, provided that film director and cast has been cached, false otherwise
     */
    public boolean hasDirectorAndCast(long filmID) {
        L.d(TAG, "hasDirectorAndCast() called, filmID: " + filmID);
        return mDirectors.containsKey(filmID) && mCast.containsKey(filmID);
    }

}
