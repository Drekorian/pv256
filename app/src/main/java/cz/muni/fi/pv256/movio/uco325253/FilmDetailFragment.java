package cz.muni.fi.pv256.movio.uco325253;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This fragment displays the detailed information about a movie.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmDetailFragment extends Fragment {

    private static final String SAVE_STATE_KEY_FILM = "film";
    private static final String SAVE_STATE_KEY_POSITION = "position";

    private View mRoot;
    private TextView mTitle;
    private ImageView mPoster;
    private Film mFilm;
    // TODO: position should be removed with the real data and image implementation
    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_detail, container, false);

        mRoot = view;
        mTitle = (TextView) view.findViewById(R.id.tvwTitle);
        mPoster = (ImageView) view.findViewById(R.id.ivwPoster);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (null != savedInstanceState && savedInstanceState.containsKey(SAVE_STATE_KEY_FILM) && savedInstanceState.containsKey(SAVE_STATE_KEY_POSITION)) {
            setFilm((Film) savedInstanceState.getParcelable(SAVE_STATE_KEY_FILM), savedInstanceState.getInt(SAVE_STATE_KEY_POSITION));
        }

        mRoot.setVisibility(null != mFilm ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * Sets film associated with the fragment.
     *
     * @param film     film to be set
     * @param position position to be set
     */
    public void setFilm(Film film, int position) {
        mFilm = film;
        // TODO: position should be removed with the real data and image implementation
        mPosition = position;
        mTitle.setText(film.getTitle());
        mPoster.setImageResource(getImageDrawable(position));
        mRoot.setVisibility(null != mFilm ? View.VISIBLE : View.INVISIBLE);
    }

    // TODO: position should be removed with the real data and image implementation
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
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVE_STATE_KEY_FILM, mFilm);
        outState.putInt(SAVE_STATE_KEY_POSITION, mPosition);
    }

}
