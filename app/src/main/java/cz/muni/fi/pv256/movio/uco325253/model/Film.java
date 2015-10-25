package cz.muni.fi.pv256.movio.uco325253.model;

/**
 * A model class that hold film data.
 * <p/>
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
     * @param coverPath   cover path to be set
     * @param title       film title to be set
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

    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof Film)) {
            return false;
        }

        Film other = (Film) o;
        return mReleaseDate == other.mReleaseDate  &&
               mCoverPath.equals(other.mCoverPath) &&
               mTitle.equals(other.mTitle);
    }

    @Override
    public int hashCode() {
        return 101 * ((int) mReleaseDate) + 211 * mCoverPath.hashCode() + 307 * mTitle.hashCode();
    }

}
