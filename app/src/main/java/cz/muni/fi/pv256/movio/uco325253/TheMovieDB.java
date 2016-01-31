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
    public static final String API_BASE_URL = "http://api.themoviedb.org";

    /**
     * The Movie DB images API base URL.
     */
    public static final String API_IMAGES_BASE_URL = "http://image.tmdb.org/t/p/original";

    /**
     * The Movie DB images API base URL
     */
    public static final String QUERY_PARAM_VALUE_AVG_RATING_DESC = "avg_rating.desc";

    /**
     * The Movie DB API key.
     */
    public static final String API_KEY = "9b423b1fe1dca6655d13348684dce5d4";

    /**
     * Shared preferences key to store the fake data flag.
     */
    public static final String SHARED_PREFERENCES_KEY_FAKE_DATA = "fake_data";

    /**
     * Default constructor. Private in order to ensure the static usage.
     */
    private TheMovieDB() {
    }

}
