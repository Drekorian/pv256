package cz.muni.fi.pv256.movio.uco325253;

/**
 * Static class that holds TheMovieDB API key.
 * <p/>
 * Created by xosvald on 29.09.2015.
 */
public class TheMovieDB {

    /**
     * HTTP schema for The Movie DB API
     */
    public static final String API_SCHEME = "http";
    /**
     * HTTP host for The Movie DB API
     */
    public static final String API_HOST = "api.themoviedb.org";
    /**
     * HTTP attribute for The Movie DB API version
     */
    public static final String API_VERSION = "3";

    /**
     * HTTP parameter for discovering movies
     */
    public static final String API_CONTROLLER_DISCOVER = "discover";
    /**
     * HTTP controller for movies
     */
    public static final String API_CONTROLLER_MOVIE = "movie";
    /**
     * HTTP parameter for movie details
     */
    public static final String API_PARAM_MOVIE = "movie";
    /**
     * HTTP parameter for movie credits
     */
    public static final String API_PARAM_CREDITS = "credits";

    /**
     * The Movie DB images API base URL.
     */
    public static final String API_IMAGES_BASE_URL = "http://image.tmdb.org/t/p/original";

    /**
     * The Movie DB API key.
     */
    public static final String API_KEY = "9b423b1fe1dca6655d13348684dce5d4";

    /**
     * Default constructor. Private in order to ensure the static usage.
     */
    private TheMovieDB() {
    }

}

