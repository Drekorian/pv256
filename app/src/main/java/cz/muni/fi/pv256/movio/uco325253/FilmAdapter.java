package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.model.BitmapUtils;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * A customer adapter for displaying movies in a grid view.
 * <p/>
 * Created by xosvald on 12.10.2015.
 */
public class FilmAdapter extends ArrayAdapter<Film> {

    private int mResource;

    /**
     * Parametric constructor. Sets context and a resource to inflate.
     *
     * @param context  Android context
     * @param resource resource to inflate
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, int resource) {
        super(context, resource);
        mResource = resource;
    }

    /**
     * Parametric constructor. Sets context, resource to inflate and text view resource to populate.
     *
     * @param context            Android context
     * @param resource           resource to inflate
     * @param textViewResourceId text view resource to populate
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mResource = resource;
    }

    /**
     * Parametric constructor. Sets context, resource to inflate and data array.
     *
     * @param context  Android context
     * @param resource resource to inflate
     * @param objects  data array
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, int resource, Film[] objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    /**
     * Parametric constructor. Sets context, resource to inflate, text view resource to populate
     * and array data.
     *
     * @param context            Android context
     * @param resource           resource to inflate
     * @param textViewResourceId text view resource to populate
     * @param objects            array data
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, int resource, int textViewResourceId, Film[] objects) {
        super(context, resource, textViewResourceId, objects);
        mResource = resource;
    }

    /**
     * Parametric constructor. Sets context, resource to inflate and data list.
     *
     * @param context  Android context
     * @param resource resource to inflate
     * @param objects  data list
     */
    public FilmAdapter(Context context, int resource, List<Film> objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    /**
     * Parametric constructor. Sets context, resource to inflate, text view resource to populate and
     * list data.
     *
     * @param context            Android context
     * @param resource           resource to inflate
     * @param textViewResourceId text view resource to populate
     * @param objects            list data
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, int resource, int textViewResourceId, List<Film> objects) {
        super(context, resource, textViewResourceId, objects);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        FilmViewHolder filmViewHolder;

        if (null == view) {
            Log.i("", "inflate radku " + position);
            view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.ivwCover);
            TextView textView = (TextView) view.findViewById(R.id.tvwName);
            filmViewHolder = new FilmViewHolder(imageView, textView);
            view.setTag(filmViewHolder);
        } else {
            Log.i("", "recyklace radku " + position);
            filmViewHolder = (FilmViewHolder) view.getTag();
        }

//        if (2 >= position % 6) {
//            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_light));
//        } else {
//            view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//        }

        final Film film = getItem(position);

        BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapUtils.calculateInSampleSize(options, filmViewHolder.imageView.getMeasuredWidth(), filmViewHolder.imageView.getMeasuredHeight());
        filmViewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), getImageDrawable(position), options));
        filmViewHolder.textView.setText(film.getTitle());
        filmViewHolder.textView.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    private int getImageDrawable(int position) {
        switch (position % 6) {
            case 0:
                return R.drawable._1;

            case 1:
                return R.drawable._2;

            case 2:
                return R.drawable._3;

            case 3:
                return R.drawable._4;

            case 4:
                return R.drawable._5;

            case 5:
                return R.drawable._6;

            default:
                throw new IllegalArgumentException("Unable to retrieve drawable for position: " + position);
        }
    }

    //    @Override
    //    public int getViewTypeCount() {
    //        return 2;
    //    }


    /**
     * A static class that serves as the film view holder.
     */
    static final class FilmViewHolder {
        /**
         * View holder image view
         */
        final ImageView imageView;
        /**
         * View holder text view
         */
        final TextView textView;

        /**
         * Parametric constructor. Sets all attributes.
         *
         * @param imageView image view to be set
         * @param textView  text view to be set
         */
        FilmViewHolder(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }
    }

}
