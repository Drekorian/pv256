package cz.muni.fi.pv256.movio.uco325253.model;

/**
 * A model class that hold film data.
 *
 * Created by xosvald on 12.10.2015.
 */
public final class Film {

    private final long mReleaseDate;
    private final String mCoverPath;
    private final String mTitle;

    /**
     * Parametric constructor. Sets all film attributes.
     *
     * @param releaseDate release date in millis to be set
     * @param coverPath cover path to be set
     * @param title film title to be set
     */
    public Film(long releaseDate, String coverPath, String title) {
        mReleaseDate = releaseDate;
        mCoverPath = coverPath;
        mTitle = title;
    }

    /**
     * Returns movie release date in milliseconds since the beginning of the computer age.
     *
     * @return movie release date in milliseconds since the beginning of the computer age
     */
    public long getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Returns movie cover path.
     *
     * @return movie cover path
     */
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

}
