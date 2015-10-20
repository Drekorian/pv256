package cz.muni.fi.pv256.movio.uco325253;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco325253.model.Film;

/**
 * This class serves as the app's main activity.
 * <p/>
 * Created by xosvald on 21.09.2015.
 */
public class MainActivity extends AppCompatActivity {

    @SuppressWarnings("FieldCanBeLocal")
    private Toolbar mToolbar;
    @SuppressWarnings("FieldCanBeLocal")
    private GridView mGvwMovies;
    @SuppressWarnings("FieldCanBeLocal")
    private ViewStub mEmptyView;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView mTvwEmpty;
    @SuppressWarnings("FieldCanBeLocal")
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    @SuppressWarnings("FieldCanBeLocal")
    private ListView mLeftDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mGvwMovies = (GridView) findViewById(android.R.id.list);
        mEmptyView = (ViewStub) findViewById(android.R.id.empty);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLeftDrawer = (ListView) findViewById(R.id.left_drawer);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        // adapter fake data
        FilmAdapter adapter = new FilmAdapter(this, R.layout.item_film, new ArrayList<Film>() {{
            add(new Film(0, "", "Doctor Who"));
            add(new Film(0, "", "The Walking Dead"));
            add(new Film(0, "", "The Big Bang Theory"));
            add(new Film(0, "", "Jurassic World"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials"));
            add(new Film(0, "", "Hotel Transylvania 2"));

            add(new Film(0, "", "Doctor Who"));
            add(new Film(0, "", "The Walking Dead"));
            add(new Film(0, "", "The Big Bang Theory"));
            add(new Film(0, "", "Jurassic World"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials"));
            add(new Film(0, "", "Hotel Transylvania 2"));

            add(new Film(0, "", "Doctor Who"));
            add(new Film(0, "", "The Walking Dead"));
            add(new Film(0, "", "The Big Bang Theory"));
            add(new Film(0, "", "Jurassic World"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials"));
            add(new Film(0, "", "Hotel Transylvania 2"));

            add(new Film(0, "", "Doctor Who"));
            add(new Film(0, "", "The Walking Dead"));
            add(new Film(0, "", "The Big Bang Theory"));
            add(new Film(0, "", "Jurassic World"));
            add(new Film(0, "", "Maze Runner: The Scorch Trials"));
            add(new Film(0, "", "Hotel Transylvania 2"));
        }});

        mGvwMovies.setAdapter(adapter);
        mGvwMovies.setEmptyView(mEmptyView);

        mTvwEmpty = (TextView) findViewById(R.id.tvwEmpty);

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
                Intent intent = new Intent(MainActivity.this, FilmDetailActivity.class);
                startActivity(intent);
            }

        });

        mLeftDrawer.setAdapter(new ArrayAdapter<>(this, R.layout.list_item_menu, R.id.tvwName, new ArrayList<String>() {{
            add("Akční");
            add("Detektivní");
            add("Horror");
            add("Sci-fi");
        }}));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);
                return true;
        }

        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        Log.d("", "" + activeNetworkInfo);
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
