package cz.muni.fi.pv256.movio.uco325253.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the cast result API data wrapper.
 * <p/>
 * Created by xosvald on 10.11.2015.
 */
public class CastWrapper {

    private static final String TAG = CastWrapper.class.getSimpleName();

    private static final String SERIALIZED_NAME_CAST = "cast";
    private static final String SERIALIZED_NAME_CREW = "crew";

    @SerializedName(SERIALIZED_NAME_CAST)
    private List<Cast> mCast;
    @SerializedName(SERIALIZED_NAME_CREW)
    private List<Cast> mCrew;

    /**
     * Returns a list of cast.
     *
     * @return a list of cast
     */
    public List<Cast> getCast() {
        L.d(TAG, "getCast() called");
        return mCast;
    }

    /**
     * Sets a list of cast.
     *
     * @param cast list of cast to be set
     */
    public void setCast(List<Cast> cast) {
        L.d(TAG, "setCast() called, cast: " + cast);
        mCast = cast;
    }

    /**
     * Returns a list of crew.
     *
     * @return a list of crew
     */
    public List<Cast> getCrew() {
        L.d(TAG, "getCrew() called");
        return mCrew;
    }

    /**
     * Sets a list of crew.
     *
     * @param crew a list of crew to be set
     */
    @SuppressWarnings("unused")
    public void setCrew(List<Cast> crew) {
        L.d(TAG, "setCrew() called, crew: " + crew);
        mCrew = crew;
    }

}
