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
    public static final String DATABASE_NAME = "films.db";

    /**
     * Database schema version
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Parametric constructor. Initializes the helper with given Android context.
     *
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
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + FilmContract.FilmEntry.TABLE_NAME + " (" +
                FilmContract.FilmEntry._ID + " INTEGER PRIMARY KEY," +
                FilmContract.FilmEntry.COLUMN_TITLE + " TEXT NOT NULL," +
                FilmContract.FilmEntry.COLUMN_OVERVIEW + " TEXT," +
                FilmContract.FilmEntry.COLUMN_RELEASE_DATE + " TEXT," +
                FilmContract.FilmEntry.COLUMN_POSTER_PATH + " TEXT," +
                FilmContract.FilmEntry.COLUMN_BACKDROP_PATH + " TEXT" +
                " );";

        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

}
