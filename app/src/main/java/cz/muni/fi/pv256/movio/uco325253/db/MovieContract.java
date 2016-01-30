package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by xosvald on 04.12.2015.
 */
public class MovieContract {

    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco325253";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_WORK_TIME = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_WORK_TIME).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WORK_TIME;

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_START_DATE_TEXT = "start_date";
        public static final String COLUMN_END_DATE_TEXT = "end_date";

        public static Uri buildWorkTimeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
