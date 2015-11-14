package cz.muni.fi.pv256.movio.uco325253.model;

import com.google.gson.annotations.SerializedName;

import cz.muni.fi.pv256.movio.uco325253.L;

/**
 * This class serves as the model for film cast.
 * <p/>
 * Created by xosvald on 10.11.2015.
 */
public class Cast {

    private static final String TAG = Cast.class.getSimpleName();

    private static final String SERIALIZED_NAME_PROFILE_PATH = "profile_path";
    private static final String SERIALIZED_NAME_NAME = "name";
    private static final String SERIALIZED_NAME_DEPARTMENT = "department";

    @SerializedName(SERIALIZED_NAME_PROFILE_PATH)
    private String mProfilePath;
    @SerializedName(SERIALIZED_NAME_NAME)
    private String mName;
    @SerializedName(SERIALIZED_NAME_DEPARTMENT)
    private String mDepartment;

    /**
     * Parametric constructor. Sets all attributes
     *
     * @param profilePath profile path to be set
     * @param name        name to be set
     */
    public Cast(String profilePath, String name) {
        mProfilePath = profilePath;
        mName = name;
    }

    /**
     * Returns cast profile path URL.
     *
     * @return cast profile path URL
     */
    public String getProfilePath() {
        L.d(TAG, "getProfilePath() called");
        return mProfilePath;
    }

    /**
     * Sets cast profile path URL.
     *
     * @param profilePath cast profile path URL to be set
     */
    @SuppressWarnings("unused")
    public void setProfilePath(String profilePath) {
        L.d(TAG, "setProfilePath() called, profilePath: " + profilePath);
        mProfilePath = profilePath;
    }

    /**
     * Returns cast name.
     *
     * @return cast name
     */
    public String getName() {
        L.d(TAG, "getName() called");
        return mName;
    }

    /**
     * Sets cast name.
     *
     * @param name cast name to be set
     */
    public void setName(String name) {
        L.d(TAG, "setName() called, name: " + name);
        mName = name;
    }

    /**
     * Returns cast department.
     *
     * @return cast department
     */
    public String getDepartment() {
        L.d(TAG, "getDepartment() called");
        return mDepartment;
    }

    /**
     * Sets the cast department.
     *
     * @param department cast department to be set
     */
    @SuppressWarnings("unused")
    public void setDepartment(String department) {
        L.d(TAG, "setDepartment() called, department: " + department);
        mDepartment = department;
    }

}
