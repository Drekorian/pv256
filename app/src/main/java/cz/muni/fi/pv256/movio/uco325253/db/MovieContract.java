package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class stores the table definitions and URIs corresponding to the movie data.
 * <p/>
 * Created by xosvald on 04.12.2015.
 */
public class MovieContract {

    /**
     * Content authority - equal to the application package
     */
    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco325253";
    /**
     * Base content uri - equal to the content protocol and the authority
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Path to a single movie details
     */
    public static final String PATH_WORK_TIME = "movie";

    /**
     * This class represents a single movie entry. The unique ID is stored in the _id column.
     */
    public static final class MovieEntry implements BaseColumns {
        /**
         * Content URI to a single movie entry
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_TIME).build();

        /**
         * Content type for a collection of movies
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;
        /**
         * Content type for a single movie
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;

        /**
         * Database table name which stores the movie entries
         */
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_NAME = "name";

        /**
         * Returns a URI for movie with given unique ID.
         *
         * @param id ID to return the URI for
         * @return a URI for movie with given unique ID
         */
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
