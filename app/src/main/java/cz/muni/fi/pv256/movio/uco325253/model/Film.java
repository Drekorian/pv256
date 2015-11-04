package cz.muni.fi.pv256.movio.uco325253.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A model class that hold film data.
 * <p/>
 * Created by xosvald on 12.10.2015.
 */
public final class Film implements Parcelable {

    private final long mReleaseDate;
    private final String mCoverPath;
    private final String mTitle;
    private final String mSection;

    /**
     * Parametric constructor. Sets all film attributes.
     *
     * @param releaseDate release date in millis to be set
     * @param coverPath   cover path to be set
     * @param title       film title to be set
     */
    public Film(long releaseDate, String coverPath, String title, String section) {
        mReleaseDate = releaseDate;
        mCoverPath = coverPath;
        mTitle = title;
        mSection = section;
    }

    /**
     * Parametric constructor. Creates the film from parcelable package.
     *
     * @param in parcelable package to create the film from
     */
    protected Film(Parcel in) {
        this.mReleaseDate = in.readLong();
        this.mCoverPath = in.readString();
        this.mTitle = in.readString();
        this.mSection = in.readString();
    }

    /**
     * Returns movie release date in milliseconds since the beginning of the computer age.
     *
     * @return movie release date in milliseconds since the beginning of the computer age
     */
    @SuppressWarnings("unused")
    public long getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Returns movie cover path.
     *
     * @return movie cover path
     */
    @SuppressWarnings("unused")
    public String getCoverPath() {
        return mCoverPath;
    }

    /**
     * Returns movie title.
     *
     * @return movie title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns film section name.
     *
     * @return film section name
     */
    public String getSection() {
        return mSection;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mReleaseDate);
        dest.writeString(this.mCoverPath);
        dest.writeString(this.mTitle);
        dest.writeString(this.mSection);
    }

    /**
     * Parcelable creator.
     */
    public static final Creator<Film> CREATOR = new Creator<Film>() {
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

}
