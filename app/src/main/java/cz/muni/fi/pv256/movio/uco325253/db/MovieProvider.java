package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the content provider for the movie entity.
 * <p/>
 * Created by xosvald on 02.12.2015.
 */
public class MovieProvider extends ContentProvider {

    private static final String TAG = MovieProvider.class.getSimpleName();

    private static final int MOVIE = 100;
    private static final int MOVIE_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private DBHelper mDBHelper;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_WORK_TIME, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_WORK_TIME + "/#", MOVIE_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        L.d(TAG, String.format("query called, Uri: %s, projection: %s, selection: %s, selectionArgs: %s, sortOrder: %s", uri, Arrays.toString(projection), selection, Arrays.toString(selectionArgs), sortOrder));

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            // single movie
            case MOVIE_ID: {
                cursor = mDBHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME, // table
                        projection, // projection (columns)
                        MovieContract.MovieEntry._ID + " = '" + ContentUris.parseId(uri) + "'", // selection (where)
                        null,      // selection arguments
                        null,      // group by
                        null,      // having
                        sortOrder  // order by
                );
                break;
            }
            case MOVIE: {
                cursor = mDBHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME, // table
                        projection,    // projection (columns)
                        selection,     // selection (where)
                        selectionArgs, // selection arguments
                        null,          // group by
                        null,          // having
                        sortOrder      // order by
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        //noinspection ConstantConditions
        ContentResolver contentResolver = getContext().getContentResolver();

        if (null != contentResolver) {
            cursor.setNotificationUri(contentResolver, uri);
        } else {
            L.e(TAG, "Content resolver is null.");
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, String.format("getType() called, Uri: %s", uri));

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;

            case MOVIE_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        L.d(TAG, String.format("insert() called, uri: %s, values: %s", uri, values));

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri movieURI;

        switch (match) {
            case MOVIE:
                long id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (0 < id)
                    movieURI = MovieContract.MovieEntry.buildMovieUri(id);
                else {
                    throw new android.database.SQLException(String.format("Failed to insert row into %s", uri));
                }

                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }

        //noinspection ConstantConditions
        getContext().getContentResolver().notifyChange(uri, null);
        return movieURI;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, String.format("delete called() uri: %s, selection: %s, selectionArgs: %s", uri, selection, Arrays.toString(selectionArgs)));

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case MOVIE:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because a null deletes all rows
        if (null == selection || rowsDeleted != 0) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, String.format("update called(), uri: %s, values: %s, selection: %s, selectionArgs: %s", uri, values, selection, Arrays.toString(selectionArgs)));

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIE:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown URI: " + uri);
        }
        if (0 != rowsUpdated) {
            //noinspection ConstantConditions
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

}
