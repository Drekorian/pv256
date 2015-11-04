package cz.muni.fi.pv256.movio.uco325253;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.lang.ref.WeakReference;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * An activity class that display film details data.
 * <p/>
 * Created by xosvald on 19.10.2015.
 */
public class FilmDetailActivity extends AppCompatActivity {

    public static final String EXTRA_KEY_FILM = "film";
    public static final String EXTRA_KEY_POSITION = "position";

    @SuppressWarnings("FieldCanBeLocal")
    private Film mFilm = null;
    @SuppressWarnings("FieldCanBeLocal")
    private int mPosition;
    @SuppressWarnings("FieldCanBeLocal")
    private Toolbar mToolbar;

    private WeakReference<FilmDetailFragment> mFilmDetailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        final Bundle extras = getIntent().getExtras();
        if (null != extras && extras.containsKey(EXTRA_KEY_FILM)) {
            mFilm = extras.getParcelable(EXTRA_KEY_FILM);
            mPosition = extras.getInt(EXTRA_KEY_POSITION);
            mFilmDetailFragment.get().setFilm(mFilm, mPosition);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof FilmDetailFragment) {
            mFilmDetailFragment = new WeakReference<>((FilmDetailFragment) fragment);
        }
    }

}
