package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This class stores the table definitions and URIs corresponding to the film data.
 * <p/>
 * Created by xosvald on 04.12.2015.
 */
public class FilmContract {

    /**
     * Content authority - equal to the application package
     */
    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco325253";
    /**
     * Base content uri - equal to the content protocol and the authority
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    /**
     * Path to a single film details
     */
    public static final String PATH_FILM = "film";

    /**
     * This class represents a single film entry. The unique ID is stored in the _id column.
     */
    public static final class FilmEntry implements BaseColumns {
        /**
         * Content URI to a single film entry
         */
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FILM).build();

        /**
         * Content type for a collection of film
         */
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_FILM;
        /**
         * Content type for a single film
         */
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_FILM;

        /**
         * Database table name which stores the film entries
         */
        public static final String TABLE_NAME = "films";

        /**
         * Column name for the film title
         */
        public static final String COLUMN_TITLE = "title";
        /**
         * Column name for the film overview
         */
        public static final String COLUMN_OVERVIEW = "overview";
        /**
         * Column name for the film year
         */
        public static final String COLUMN_RELEASE_DATE = "release_date";
        /**
         * Column name for the film poster path
         */
        public static final String COLUMN_POSTER_PATH = "poster_path";
        /**
         * Column name for the film backdrop path
         */
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";

        /**
         * Returns a URI for film with given unique ID.
         *
         * @param id ID to return the URI for
         * @return a URI for film with given unique ID
         */
        public static Uri buildFilmUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

}
