package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersSimpleArrayAdapter;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * A customer adapter for displaying movies in a grid view.
 * <p/>
 * Created by xosvald on 12.10.2015.
 */
public class FilmAdapter extends StickyGridHeadersSimpleArrayAdapter<Film> {

    private static final String TAG = FilmAdapter.class.getSimpleName();

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
        L.d(TAG, "getView() called, position: " + position + ", convertView: " + convertView + ", parent: " + parent);

        View view = convertView;

        final Film film = getItem(position);

        if (null == view) {
            L.i("", "inflate radku " + position);
            view = LayoutInflater.from(mContext).inflate(mItemResource, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.ivwBackdrop);
            TextView textView = (TextView) view.findViewById(R.id.tvwName);
            FilmViewHolder filmViewHolder = new FilmViewHolder(film, imageView, textView);
            view.setTag(filmViewHolder);
        } else {
            L.i("", "recyklace radku " + position);
        }

        final FilmViewHolder filmViewHolder = (FilmViewHolder) view.getTag();

        filmViewHolder.film = film;
        filmViewHolder.textView.setText(film.getTitle());
        filmViewHolder.textView.setVisibility(View.INVISIBLE);

        Picasso picasso = Picasso.with(mContext);

        if (BuildConfig.DEBUG) {
            picasso.setIndicatorsEnabled(true);
        }

        if (null != film.getPosterPath()) {
            L.d(TAG, "Loading poster for " + film.getTitle() + ".");
            picasso.load(TheMovieDB.API_IMAGES_BASE_URL + film.getPosterPath())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.film_placeholder)
                    .into(filmViewHolder.imageView);
        } else {
            L.i(TAG, "Poster path for " + film.getTitle() + " is null.");
            filmViewHolder.imageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.film_placeholder));
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        L.d(TAG, "getViewTypeCount() called");
        return 2;
    }

    @Override
    public long getHeaderId(int position) {
        L.d(TAG, "getHeaderId() called, position: " + position);
        return getItem(position).getSection().hashCode();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        L.d(TAG, "getHeaderView() called, position: " + position + ", convertView: " + convertView + ", parent: " + parent);

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
         * Film data.
         */
        public Film film;

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
         * @param film      film data to be set
         * @param imageView image view to be set
         * @param textView  text view to be set
         */
        FilmViewHolder(Film film, ImageView imageView, TextView textView) {
            this.film = film;
            this.imageView = imageView;
            this.textView = textView;
        }

    }

}
