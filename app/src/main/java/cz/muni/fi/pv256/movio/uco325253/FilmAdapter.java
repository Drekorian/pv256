package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * A customer adapter for displaying movies in a grid view.
 * <p/>
 * Created by xosvald on 12.10.2015.
 */
public class FilmAdapter extends StickyGridHeadersSimpleArrayAdapter<Film> {

    private Context mContext;
    private int mHeaderResource;
    private int mItemResource;

    /**
     * Parametric constructor. Sets Android context, list of items, header resource ID and item
     * resource ID.
     *
     * @param context     Android context to be set
     * @param items       a list of items to be set
     * @param headerResId header resource ID to be set
     * @param itemResId   item resource ID to be set
     */
    public FilmAdapter(Context context, List<Film> items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        mContext = context;
        mHeaderResource = headerResId;
        mItemResource = itemResId;
    }

    /**
     * Parametric constructor. Sets Android context, array of items, header resource ID and item
     * resource ID.
     *
     * @param context     Android context to be set
     * @param items       an array of items to be set
     * @param headerResId header resource ID to be set
     * @param itemResId   item resource ID to be set
     */
    @SuppressWarnings("unused")
    public FilmAdapter(Context context, Film[] items, int headerResId, int itemResId) {
        super(context, items, headerResId, itemResId);
        mContext = context;
        mHeaderResource = headerResId;
        mItemResource = itemResId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            Log.i("", "inflate radku " + position);
            view = LayoutInflater.from(mContext).inflate(mItemResource, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.ivwCover);
            TextView textView = (TextView) view.findViewById(R.id.tvwName);
            FilmViewHolder filmViewHolder = new FilmViewHolder(imageView, textView);
            view.setTag(filmViewHolder);
        } else {
            Log.i("", "recyklace radku " + position);
        }

//        if (2 >= position % 6) {
//            view.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.primary_light));
//        } else {
//            view.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
//        }

        final Film film = getItem(position);

        FilmViewHolder filmViewHolder = (FilmViewHolder) view.getTag();

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapUtils.calculateInSampleSize(options, filmViewHolder.imageView.getMeasuredWidth(), filmViewHolder.imageView.getMeasuredHeight());
//        filmViewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), getImageDrawable(position), options));
        filmViewHolder.imageView.setImageResource(getImageDrawable(position));
        filmViewHolder.textView.setText(film.getTitle());
        filmViewHolder.textView.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getHeaderId(int position) {
        return getItem(position).getSection().hashCode();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        final Film film = getItem(position);
        HeaderViewHolder headerViewHolder;

        if (null == convertView) {
            view = LayoutInflater.from(mContext).inflate(mHeaderResource, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.tvwName);
            headerViewHolder = new HeaderViewHolder(textView);
            view.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) view.getTag();
        }

        headerViewHolder.textView.setText(film.getSection());
        return view;
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

    /**
     * A static class that serves as the view holder for sticky grid headers.
     */
    static final class HeaderViewHolder {

        /**
         * View holder text view
         */
        public final TextView textView;

        /**
         * Parametric constructor. Sets view holder text view.
         *
         * @param textView text view to be set
         */
        HeaderViewHolder(TextView textView) {
            this.textView = textView;
        }

    }

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
