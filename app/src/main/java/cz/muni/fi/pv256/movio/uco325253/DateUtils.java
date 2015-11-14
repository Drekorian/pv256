package cz.muni.fi.pv256.movio.uco325253;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Static helper class that handles date manipulation.
 * <p/>
 * Created by xosvald on 07.11.2015.
 */
public class DateUtils {

    private static final String TAG = DateUtils.class.getSimpleName();

    private static final String THE_MOVIE_DB_API_FORMAT = "dd-MM-yyyy";
    private static final int DAY = 1;
    private static final int WEEK = 7;

    /**
     * Default constructor. Private in order to ensure the static usage.
     */
    private DateUtils() {
    }

    /**
     * Returns a date one week before the current date.
     *
     * @param calendar current date
     * @return a date one week before the current date
     */
    public static Calendar aWeekAgoFrom(Calendar calendar) {
        L.d(TAG, "aWeekAgoFrom() called, calendar: " + calendar);
        calendar.add(Calendar.DAY_OF_MONTH, -WEEK);
        return calendar;
    }

    /**
     * Returns a date one week after the current date.
     *
     * @param calendar current date
     * @return a date one week after the current date
     */
    public static Calendar aWeekFrom(Calendar calendar) {
        L.d(TAG, "aWeekFrom() called, calendar: " + calendar);
        calendar.add(Calendar.DAY_OF_MONTH, WEEK);
        return calendar;
    }

    /**
     * Returns a date one day after the current date.
     *
     * @param calendar current date
     * @return a date one day after the current date
     */
    public static Calendar tomorrow(Calendar calendar) {
        L.d(TAG, "tomorrow() called, calendar: " + calendar);
        calendar.add(Calendar.DAY_OF_MONTH, DAY);
        return calendar;
    }

    /**
     * Formats the date in the format for the The Movie DB API format.
     *
     * @param calendar a date to format
     * @return The Movie DB API formatted date
     */
    @SuppressLint("SimpleDateFormat")
    public static String formatDate(Calendar calendar) {
        L.d(TAG, "formatDate() called, calendar: " + calendar);
        SimpleDateFormat dateFormat = new SimpleDateFormat(THE_MOVIE_DB_API_FORMAT);
        return dateFormat.format(calendar.getTime());
    }

}
