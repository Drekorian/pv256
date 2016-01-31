package cz.muni.fi.pv256.movio.uco325253.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * A model class that hold film data.
 * <p/>
 * Created by xosvald on 12.10.2015.
 */
public final class Film implements Parcelable {

    private static final String TAG = Film.class.getSimpleName();

    private static final String SERIALIZED_NAME_BACKDROP_PATH = "backdrop_path";
    private static final String SERIALIZED_NAME_ID = "id";
    private static final String SERIALIZED_NAME_OVERVIEW = "overview";
    private static final String SERIALIZED_NAME_RELEASE_DATE = "release_date";
    private static final String SERIALIZED_NAME_POSTER_PATH = "poster_path";
    private static final String SERIALIZED_NAME_TITLE = "title";

    @SuppressWarnings("SimpleDateFormat")
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @SerializedName(SERIALIZED_NAME_BACKDROP_PATH)
    private String mBackdropPath;
    @SerializedName(SERIALIZED_NAME_ID)
    private long id;
    @SerializedName(SERIALIZED_NAME_OVERVIEW)
    private String mOverview;
    @SerializedName(SERIALIZED_NAME_RELEASE_DATE)
    private String mReleaseDate;
    @SerializedName(SERIALIZED_NAME_POSTER_PATH)
    private String mPosterPath;
    @SerializedName(SERIALIZED_NAME_TITLE)
    private String mTitle;

    private String mSection;

    /**
     * Default constructor. Required by the GSON framework.
     */
    @SuppressWarnings("unused")
    public Film() {
    }

    /**
     * Returns the film backdrop path.
     *
     * @return film backdrop path
     */
    public String getBackdropPath() {
        L.d(TAG, "getBackdropPath() called");
        return mBackdropPath;
    }

    /**
     * Sets film backdrop path.
     *
     * @param backdropPath backdrop path to be set
     */
    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    /**
     * Returns a unique film ID.
     *
     * @return a unique film ID
     */
    public long getId() {
        L.d(TAG, "getId() called");
        return id;
    }

    /**
     * Sets film unique ID.
     *
     * @param id unique ID to be set
     */
    public void setId(long id) {
        L.d(TAG, "setId() called, id: " + id);
        this.id = id;
    }

    /**
     * Returns the film release date.
     *
     * @return film release date
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Sets film release date.
     *
     * @param releaseDate release date to be set
     */
    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    /**
     * Returns the film release date.
     *
     * @return film release date
     */
    public String formatReleaseDate() {
        L.d(TAG, "formatReleaseDate()");

        // replace default short pattern with full year pattern
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT);
        String newPattern = simpleDateFormat.toPattern().replace("yy", "yyyy");
        simpleDateFormat.applyPattern(newPattern);

        if (null == mReleaseDate) {
            return null;
        }

        Calendar calendar = new GregorianCalendar();

        try {
            calendar.setTimeInMillis(DATE_FORMAT.parse(mReleaseDate).getTime());
            return simpleDateFormat.format(calendar.getTime());
        } catch (ParseException ex) {
            L.e(TAG, "Unable to parse release date: " + mReleaseDate);
        }

        return null;
    }

    /**
     * Returns film cover path.
     *
     * @return film cover path
     */
    public String getPosterPath() {
        L.d(TAG, "getPosterPath()");
        return mPosterPath;
    }

    /**
     * Sets film poster path.
     *
     * @param posterPath poster path to be set
     */
    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    /**
     * Returns film title.
     *
     * @return film title
     */
    public String getTitle() {
        L.d(TAG, "getTitle() called");
        return mTitle;
    }

    /**
     * Sets film title.
     *
     * @param title film title to be set
     */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Returns film section name.
     *
     * @return film section name
     */
    public String getSection() {
        L.d(TAG, "getSection() called");
        return mSection;
    }

    /**
     * Sets film section.
     *
     * @param section film section to be set
     */
    public void setSection(String section) {
        L.d(TAG, "setSection() called, section: " + section);
        mSection = section;
    }

    /**
     * Returns film overview.
     *
     * @return film overview
     */
    public String getOverview() {
        L.d(TAG, "getOverview() called");
        return mOverview;
    }

    /**
     * Sets film overview.
     *
     * @param overview film overview to be set
     */
    public void setOverview(String overview) {
        L.d(TAG, "setOverview() called, overview: " + overview);
        mOverview = overview;
    }

    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof Film)) {
            return false;
        }

        Film other = (Film) o;
        return id == other.id &&
                mTitle.equals(other.mTitle);
    }

    @Override
    public int hashCode() {
        return (int) id + 101 * mTitle.hashCode();
    }

    /*
     * Auto-generated. DO NOT MODIFY manually, re-create via the Android Studio plug-in.
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mBackdropPath);
        dest.writeLong(this.id);
        dest.writeString(this.mOverview);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mTitle);
        dest.writeString(this.mSection);
    }

    protected Film(Parcel in) {
        this.mBackdropPath = in.readString();
        this.id = in.readLong();
        this.mOverview = in.readString();
        this.mReleaseDate = in.readString();
        this.mPosterPath = in.readString();
        this.mTitle = in.readString();
        this.mSection = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

}
