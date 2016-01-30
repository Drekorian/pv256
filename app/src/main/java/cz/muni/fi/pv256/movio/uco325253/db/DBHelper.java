package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class serves as an implementation of SQLiteOpenHelper which handles basic DB operation like
 * creating database, upgrades and such.
 * <p/>
 * Created by xosvald on 02.12.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    /**
     * Internal storage DB file name
     */
    public static final String DATABASE_NAME = "movies.db";

    /**
     * Database schema version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Parametric constructor. Initializes the helper with given Android context.
     * @param context Android context to initialize the helper with
     */
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create tables, provided that it hasn't been created before
        createTableMovies(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void createTableMovies(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.MovieEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                // TODO: remove me
                //"UNIQUE (" + MovieContract.MovieEntry.COLUMN_START_DATE_TEXT + ", " + MovieContract.MovieEntry.COLUMN_END_DATE_TEXT + ") ON CONFLICT REPLACE" +
                " );";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

}
