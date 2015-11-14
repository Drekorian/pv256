package cz.muni.fi.pv256.movio.uco325253.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the model for the film results list.
 * <p/>
 * Created by xosvald on 07.11.2015.
 */
public class ResultWrapper {

    private static final String TAG = ResultWrapper.class.getSimpleName();

    private static final String SERIALIZED_NAME_RESULTS = "results";

    @SerializedName(SERIALIZED_NAME_RESULTS)
    private Film[] mResults;

    /**
     * Default constructor. Required by the GSON framework.
     */
    public ResultWrapper() {
    }

    /**
     * Returns the list of results.
     *
     * @return the list of results
     */
    public Film[] getResults() {
        L.d(TAG, "getResults() called");
        return mResults;
    }

    /**
     * Sets the list of results.
     *
     * @param results the list of results to be set
     */
    @SuppressWarnings("unused")
    public void setResults(Film[] results) {
        L.d(TAG, "getResults() called, results: " + Arrays.toString(results));
        mResults = results;
    }

}
