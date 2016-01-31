package cz.muni.fi.pv256.movio.uco325253;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco325253.model.Film;


/**
 * This class serves as the app's main activity.
 * <p/>
 * Created by xosvald on 21.09.2015.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int POSITION_DISCOVER = 0;
    private static final int POSITION_FAVORITES = 1;

    private boolean mTablet;
    @SuppressWarnings("FieldCanBeLocal")
    private Toolbar mToolbar;
    @SuppressWarnings("FieldCanBeLocal")
    private Spinner mSelection;
    @SuppressWarnings("FieldCanBeLocal")
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @SuppressWarnings("FieldCanBeLocal")
    private ListView mLeftDrawer;

    private WeakReference<FilmListFragment> mFilmListFragment;
    private WeakReference<FilmDetailFragment> mFilmDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        L.d(TAG, "onCreate() called, savedInstanceState: " + savedInstanceState);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTablet = getResources().getBoolean(R.bool.tablet);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSelection = (Spinner) findViewById(R.id.sprSelection);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_toolbar, new String[]{getString(R.string.section_discover), getString(R.string.section_favorites)});
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_toolbar);

        mSelection.setAdapter(adapter);
        mSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final FilmListFragment filmListFragment = mFilmListFragment.get();

                switch (position) {
                    case POSITION_DISCOVER:
                        filmListFragment.loadDiscoverFilms();
                        break;

                    case POSITION_FAVORITES:
                        filmListFragment.loadFavoriteFilms();
                        break;

                    default:
                        throw new IllegalStateException(String.format("Unknown state: %s", position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (ListView) findViewById(R.id.left_drawer);

        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();

        if (null != actionBar) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        } else {
            L.e(TAG, "ActionBar is null! This should never happen.");
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mLeftDrawer.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_menu, R.id.tvwName, new ArrayList<String>() {{
            add("Akční");
            add("Detektivní");
            add("Horror");
            add("Sci-fi");
        }}));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        L.d(TAG, "onOptionsItemSelected() called, item: " + item);

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);
                return true;
        }

        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        L.d(TAG, "onPostCreate() called, savedInstanceState: " + savedInstanceState);
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        L.d(TAG, "onAttachFragment() called, fragment: " + fragment);

        super.onAttachFragment(fragment);

        // store a reference to the list fragment
        if (fragment instanceof FilmListFragment) {
            mFilmListFragment = new WeakReference<>((FilmListFragment) fragment);
        }

        // store a reference to the detail fragment
        if (fragment instanceof FilmDetailFragment) {
            mFilmDetailFragment = new WeakReference<>((FilmDetailFragment) fragment);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        L.d(TAG, "onConfigurationChanged() called, newConfig: " + newConfig);

        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
        mTablet = getResources().getBoolean(R.bool.tablet);
    }

    /**
     * Displays film detail for given film either in the new activity or detail fragment, based on
     * the current configuration.
     *
     * @param film film to display
     */
    public void displayFilmDetail(Film film) {
        L.d(TAG, "displayFilmDetail() called, film: " + film);

        if (mTablet) {
            if (null != mFilmDetailFragment) {
                FilmDetailFragment filmDetailFragment = mFilmDetailFragment.get();

                if (null != filmDetailFragment) {
                    filmDetailFragment.setFilm(film);
                }
            }
        } else {
            Intent intent = new Intent(this, FilmDetailActivity.class);
            intent.putExtra(FilmDetailActivity.EXTRA_KEY_FILM, film);
            startActivity(intent);
        }
    }

}
