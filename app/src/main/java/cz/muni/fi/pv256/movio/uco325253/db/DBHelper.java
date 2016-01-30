package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xosvald on 02.12.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movies.db";

    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableMovies(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTableMovies(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
            MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
            MovieContract.MovieEntry.COLUMN_START_DATE_TEXT + " TEXT NOT NULL, " +
            MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + " TEXT," +
            "UNIQUE (" + MovieContract.MovieEntry.COLUMN_START_DATE_TEXT + ", " + MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + ") ON CONFLICT REPLACE" +
        " );";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

}
