package cz.muni.fi.pv256.movio.uco325253.model;

import android.graphics.BitmapFactory;

/**
 * A static helper class that calculates Android bitmap scaling.
 * <p/>
 * Created by xosvald on 20.10.2015.
 */
public class BitmapUtils {

    /**
     * Calculates Android bitmap scaling value.
     * <p/>
     *
     * @param options   options to configure the value into
     * @param reqWidth  required bitmap width
     * @param reqHeight required bitmap height
     * @return Android bitmap scaling value
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private BitmapUtils() {
    }

}
