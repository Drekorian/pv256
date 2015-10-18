package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * Created by Marek on 12.10.2015.
 */
public class FilmAdapter extends ArrayAdapter<Film> {

    private int mResource;

    public FilmAdapter(Context context, int resource) {
        super(context, resource);
        mResource = resource;
    }

    public FilmAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        mResource = resource;
    }

    public FilmAdapter(Context context, int resource, Film[] objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    public FilmAdapter(Context context, int resource, int textViewResourceId, Film[] objects) {
        super(context, resource, textViewResourceId, objects);
        mResource = resource;
    }

    public FilmAdapter(Context context, int resource, List<Film> objects) {
        super(context, resource, objects);
        mResource = resource;
    }

    public FilmAdapter(Context context, int resource, int textViewResourceId, List<Film> objects) {
        super(context, resource, textViewResourceId, objects);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (null == view) {
            Log.i("", "inflate radku " + position);
            view = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.ivwCover);
            TextView textView = (TextView) view.findViewById(R.id.tvwName);
            FilmViewHolder filmViewHolder = new FilmViewHolder(getItem(position), imageView, textView);
            view.setTag(filmViewHolder);
        } else {
            Log.i("", "recyklace radku " + position);
        }

        final Film film = getItem(position);

        FilmViewHolder filmViewHolder = (FilmViewHolder) view.getTag();
        filmViewHolder.imageView.setImageResource(getImageDrawable(position));
        filmViewHolder.textView.setText(film.getTitle());
        filmViewHolder.textView.setVisibility(View.INVISIBLE);

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

    @Override
    public int getViewTypeCount() {
//        return super.getViewTypeCount();
        return 2;
    }

    static final class FilmViewHolder {
        final Film film;
        final ImageView imageView;
        final TextView textView;

        FilmViewHolder(Film film, ImageView imageView, TextView textView) {
            this.film = film;
            this.imageView = imageView;
            this.textView = textView;
        }
    }

}
