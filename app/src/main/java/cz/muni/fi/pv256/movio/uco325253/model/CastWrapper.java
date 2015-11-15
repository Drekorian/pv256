package cz.muni.fi.pv256.movio.uco325253.model;

import com.google.gson.annotations.SerializedName;

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

    @SuppressWarnings("unused")
    @SerializedName(SERIALIZED_NAME_CAST)
    private Cast[] mCast;
    @SuppressWarnings("unused")
    @SerializedName(SERIALIZED_NAME_CREW)
    private Cast[] mCrew;

    /**
     * Returns a list of cast.
     *
     * @return a list of cast
     */
    public Cast[] getCast() {
        L.d(TAG, "getCast() called");
        return mCast;
    }

    /**
     * Returns a list of crew.
     *
     * @return a list of crew
     */
    public Cast[] getCrew() {
        L.d(TAG, "getCrew() called");
        return mCrew;
    }

}
