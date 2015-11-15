package cz.muni.fi.pv256.movio.uco325253;

import cz.muni.fi.pv256.movio.uco325253.model.CastWrapper;
import cz.muni.fi.pv256.movio.uco325253.model.Film;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * This interfaces describes the The Movie DB REST API.
 * <p/>
 * Created by xosvald on 14.11.2015.
 */
public interface TheMovieDBAPI {

    String QUERY_PARAM_API_KEY = "api_key";
    String QUERY_PARAM_SORT_BY = "sort_by";
    String QUERY_PARAM_PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte";
    String QUERY_PARAM_PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte";
    String PATH_PARAM_ID = "id";

    /**
     * Loads the list of films released between the given interval.
     *
     * @param apiKey The Movie DB API key
     * @param sortBy sort key to sort by
     * @param from   timestamp to filter the films from
     * @param to     timestamp to filter the films to
     * @return list of films released between the given interval
     */
    @GET("/3/discover/movie")
    Call<Film[]> loadUpcomingFilms(
            @Query(QUERY_PARAM_API_KEY) String apiKey,
            @Query(QUERY_PARAM_SORT_BY) String sortBy,
            @Query(QUERY_PARAM_PRIMARY_RELEASE_DATE_GTE) String from,
            @Query(QUERY_PARAM_PRIMARY_RELEASE_DATE_LTE) String to
    );

    /**
     * Loads the film details for given film.
     *
     * @param id     unique film ID
     * @param apiKey The Movie DB API key
     * @return details for given film
     */
    @GET("/3/movie/{id}/credits")
    Call<CastWrapper> loadCastAndCrew(
            @Path(PATH_PARAM_ID) long id,
            @Query(QUERY_PARAM_API_KEY) String apiKey
    );

}
