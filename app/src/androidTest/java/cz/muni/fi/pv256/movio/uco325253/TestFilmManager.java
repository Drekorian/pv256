package cz.muni.fi.pv256.movio.uco325253;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco325253.db.FilmContract;
import cz.muni.fi.pv256.movio.uco325253.db.FilmManager;
import cz.muni.fi.pv256.movio.uco325253.model.Film;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * A test case for {@link FilmManager} class.
 */
public class TestFilmManager extends AndroidTestCase {

    private static final String TAG = TestFilmManager.class.getSimpleName();

    private FilmManager mFilmManager;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mFilmManager = new FilmManager(mContext);
        truncateTable();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        truncateTable();
    }

    /**
     * A test case for getAll() method.
     *
     * @throws Exception exception provided that something goes wrong
     */
    public void testGetWorkTimesInDay() throws Exception {
        L.d(TAG, "testGetWorkTimesInDay()");

        List<Film> expectedFilms = new ArrayList<>(2);

        Film film1 = createFilm(1, "Title 1");
        Film film2 = createFilm(2, "Title 2");

        expectedFilms.add(film1);
        expectedFilms.add(film2);

        mFilmManager.add(film1);
        mFilmManager.add(film2);

        List<Film> films = mFilmManager.getAll();
        L.d(TAG, films.toString());

        assertThat(films.size(), is(2));
        assertThat(expectedFilms, equalTo(films));
    }

    private void truncateTable() {
        mContext.getContentResolver().delete(
                FilmContract.FilmEntry.CONTENT_URI, // URI
                null, // selection (where)
                null  // selection args
        );
    }

    private Film createFilm(long id, String title) {
        Film film = new Film();
        film.setId(id);
        film.setTitle(title);

        return film;
    }

}
