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

    private static final String TAG = FilmDetailActivity.class.getSimpleName();

    public static final String EXTRA_KEY_FILM = "film";

    @SuppressWarnings("FieldCanBeLocal")
    private Film mFilm = null;
    @SuppressWarnings("FieldCanBeLocal")
    private Toolbar mToolbar;

    private WeakReference<FilmDetailFragment> mFilmDetailFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "onCreate() called, savedInstanceState: " + savedInstanceState);

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

            if (null != mFilm) {
                getSupportActionBar().setTitle(mFilm.getTitle());
            }
        }
    }

    @Override
    protected void onStart() {
        L.d(TAG, "onCreate() called");
        super.onStart();
        mFilmDetailFragment.get().setFilm(mFilm);
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        L.d(TAG, "onAttachFragment() called, fragment: " + fragment);

        super.onAttachFragment(fragment);
        if (fragment instanceof FilmDetailFragment) {
            mFilmDetailFragment = new WeakReference<>((FilmDetailFragment) fragment);
        }
    }

}
