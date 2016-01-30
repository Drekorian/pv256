package cz.muni.fi.pv256.movio.uco325253.db;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Movie;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * Created by Marek on 04.12.2015.
 */
public class MovieManager { public static final int COL_WORK_TIME_ID = 0;
    /*
    public static final int COL_WORK_TIME_START_DATE = 1;
    public static final int COL_WORK_TIME_END_DATE = 2;
    private static final String[] WORK_TIME_COLUMNS = {
            WorkTimeEntry._ID,
            WorkTimeEntry.COLUMN_START_DATE_TEXT,
            WorkTimeEntry.COLUMN_END_DATE_TEXT
    };

    private static final String LOCAL_DATE_FORMAT = "yyyyMMdd";

    private static final String WHERE_ID = WorkTimeEntry._ID + " = ?";
    private static final String WHERE_DAY = WorkTimeEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + WorkTimeEntry.COLUMN_START_DATE_TEXT + ",1,8) = ? OR " + "substr(" + WorkTimeEntry.COLUMN_END_DATE_TEXT + ",1,8) = ?";
    private static final String WHERE_DATES = WorkTimeEntry.COLUMN_END_DATE_TEXT + " IS NOT NULL AND substr(" + WorkTimeEntry.COLUMN_END_DATE_TEXT + ",1,8) >= ? AND " + "substr(" + WorkTimeEntry.COLUMN_START_DATE_TEXT + ",1,8) <= ?";

    private Context mContext;

    public MovieManager(Context context) {
        mContext = context.getApplicationContext();
    }

    //todo neukladat casy, co jsou ta stejna minuta
    public void createWorkTime(Film film) {
        if (film == null) {
            throw new NullPointerException("film == null");
        }

        if (film.getId() != null) {
            throw new IllegalStateException("film id shouldn't be set");
        }

        if (film.getStartDate() == null) {
            throw new IllegalStateException("film startDate cannot be null");
        }

        if (film.getEndDate() != null && film.getEndDate().isBefore(film.getStartDate())) {
            throw new IllegalStateException("film endDate cannot be before startDate");
        }

        film.setId(ContentUris.parseId(mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, prepareWorkTimeValues(film))));
    }

    public List<WorkTime> getWorkTimesInDay(LocalDate day) {
        if (day == null) {
            throw new NullPointerException("day == null");
        }

        Cursor cursor = mContext.getContentResolver().query(WorkTimeEntry.CONTENT_URI, WORK_TIME_COLUMNS, WHERE_DAY, new String[]{day.toString(LOCAL_DATE_FORMAT)}, null);
        if (cursor != null && cursor.moveToFirst()) {
            List<WorkTime> workTimes = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    workTimes.add(getWorkTime(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return workTimes;
        }

        return Collections.emptyList();
    }

    public List<WorkTime> getWorkTimesInInterval(Interval interval) {
        if (interval == null) {
            throw new NullPointerException("interval == null");
        }

        Cursor cursor = mContext.getContentResolver().query(WorkTimeEntry.CONTENT_URI, WORK_TIME_COLUMNS, WHERE_DATES, new String[]{interval.getStart().toString(LOCAL_DATE_FORMAT), interval.getEnd().toString(LOCAL_DATE_FORMAT)}, null);

        if (cursor != null && cursor.moveToFirst()) {
            List<WorkTime> workTimes = new ArrayList<>(cursor.getCount());
            try {
                while (!cursor.isAfterLast()) {
                    workTimes.add(getWorkTime(cursor));
                    cursor.moveToNext();
                }
            } finally {
                cursor.close();
            }
            return workTimes;
        }

        return Collections.emptyList();
    }

    public void updateWorkTime(WorkTime workTime) {
        if (workTime == null) {
            throw new NullPointerException("workTime == null");
        }
        if (workTime.getId() == null) {
            throw new IllegalStateException("workTime id cannot be null");
        }
        if (workTime.getStartDate() == null) {
            throw new IllegalStateException("workTime startDate cannot be null");
        }
        if (workTime.getEndDate() != null && workTime.getEndDate().isBefore(workTime.getStartDate())) {
            throw new IllegalStateException("workTime endDate cannot be before startDate");
        }

        mContext.getContentResolver().update(WorkTimeEntry.CONTENT_URI, prepareWorkTimeValues(workTime), WHERE_ID, new String[]{String.valueOf(workTime.getId())});
    }

    public void deleteWorkTime(WorkTime workTime) {
        if (workTime == null) {
            throw new NullPointerException("workTime == null");
        }
        if (workTime.getId() == null) {
            throw new IllegalStateException("workTime id cannot be null");
        }

        mContext.getContentResolver().delete(WorkTimeEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(workTime.getId())});
    }

    private ContentValues prepareWorkTimeValues(WorkTime workTime) {
        ContentValues values = new ContentValues();
        values.put(WorkTimeEntry.COLUMN_START_DATE_TEXT, WorkTimeContract.getDbDateString(workTime.getStartDate()));
        values.put(WorkTimeEntry.COLUMN_END_DATE_TEXT, workTime.getEndDate() != null ? WorkTimeContract.getDbDateString(workTime.getEndDate()) : null);
        return values;
    }

    private WorkTime getWorkTime(Cursor cursor) {
        WorkTime workTime = new WorkTime();
        workTime.setId(cursor.getLong(COL_WORK_TIME_ID));
        workTime.setStartDate(WorkTimeContract.getDateFromDb(cursor.getString(COL_WORK_TIME_START_DATE)));
        String endDate = cursor.getString(COL_WORK_TIME_END_DATE);
        if (endDate != null) {
            workTime.setEndDate(WorkTimeContract.getDateFromDb(endDate));
        }
        return workTime;
    }
    */
}
