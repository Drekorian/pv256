package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This fragment displays a list of films.
 * <p/>
 * Created by xosvald on 25.10.2015.
 */
public class FilmListFragment extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private StickyGridHeadersGridView mGvwMovies;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewStub mEmptyView;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView mTvwEmpty;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_film_list, container, false);

        mGvwMovies = (StickyGridHeadersGridView) view.findViewById(android.R.id.list);
        mEmptyView = (ViewStub) view.findViewById(android.R.id.empty);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // adapter fake data
        final FilmAdapter adapter = new FilmAdapter(getActivity(), new ArrayList<Film>() {{
            add(new Film(0, "", "Doctor Who", "Opening This Week"));
            add(new Film(0, "", "The Walking Dead", "Opening This Week"));
            add(new Film(0, "", "The Big Bang Theory", "Opening This Week"));
            add(new Film(0, "", "Jurassic World", "Opening This Week"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials", "Opening This Week"));
            add(new Film(0, "", "Hotel Transylvania 2", "Opening This Week"));

            add(new Film(0, "", "Doctor Who", "Opening This Week"));
            add(new Film(0, "", "The Walking Dead", "Opening This Week"));
            add(new Film(0, "", "The Big Bang Theory", "Opening This Week"));
            add(new Film(0, "", "Jurassic World", "Opening This Week"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials", "Opening This Week"));
            add(new Film(0, "", "Hotel Transylvania 2", "Opening This Week"));

            add(new Film(0, "", "Doctor Who", "In Theaters Now"));
            add(new Film(0, "", "The Walking Dead", "In Theaters Now"));
            add(new Film(0, "", "The Big Bang Theory", "In Theaters Now"));
            add(new Film(0, "", "Jurassic World", "In Theaters Now"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials", "In Theaters Now"));
            add(new Film(0, "", "Hotel Transylvania 2", "In Theaters Now"));

            add(new Film(0, "", "Doctor Who", "In Theaters Now"));
            add(new Film(0, "", "The Walking Dead", "In Theaters Now"));
            add(new Film(0, "", "The Big Bang Theory", "In Theaters Now"));
            add(new Film(0, "", "Jurassic World", "In Theaters Now"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials", "In Theaters Now"));
            add(new Film(0, "", "Hotel Transylvania 2", "In Theaters Now"));
        }}, R.layout.list_item_film_header, R.layout.item_film);

        mGvwMovies.setAdapter(adapter);
        mGvwMovies.setEmptyView(mEmptyView);

        mTvwEmpty = (TextView) view.findViewById(R.id.tvwEmpty);

        if (null != mTvwEmpty) {
            mTvwEmpty.setText(isNetworkAvailable() ? R.string.no_data : R.string.no_connection);
        }

        final AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                FilmAdapter.FilmViewHolder viewHolder = (FilmAdapter.FilmViewHolder) view.getTag();

                if (View.VISIBLE == viewHolder.textView.getVisibility()) {
                    viewHolder.textView.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.textView.setVisibility(View.VISIBLE);
                }

                return true;
            }
        };

        mGvwMovies.setOnItemLongClickListener(longClickListener);
        mGvwMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Film film = adapter.getItem(position);
                getMainActivity().displayFilmDetail(film, position);
            }

        });
    }

    private MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("", "" + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
