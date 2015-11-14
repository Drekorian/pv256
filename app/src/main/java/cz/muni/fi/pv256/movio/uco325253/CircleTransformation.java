package cz.muni.fi.pv256.movio.uco325253;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * This transformation transforms the input bitmap into a circle image bitmap.
 * <p/>
 * Created by xosvald on 11.11.2015.
 */
public class CircleTransformation implements Transformation {

    private static final String TAG = CircleTransformation.class.getSimpleName();

    private static final String TRANSFORMATION_KEY = "transformationCircle";

    @Override
    public Bitmap transform(Bitmap source) {
        L.d(TAG, "transform() called, source: " + source);

        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float radius = size / 2f - 1;
        canvas.drawCircle(radius, radius, radius, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        L.d(TAG, "key() called");
        return TRANSFORMATION_KEY;
    }

}
