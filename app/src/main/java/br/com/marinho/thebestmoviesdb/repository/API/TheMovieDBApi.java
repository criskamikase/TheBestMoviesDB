package br.com.marinho.thebestmoviesdb.repository.API;

import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDetailResponseDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Marinho on 21/09/17.
 */

public interface TheMovieDBApi {

    @GET("movie/now_playing")
    Call<MovieResponseDTO<MovieDTO>> listMovies(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("page") int page,
                                                @Query("region") String region);

    @GET("movie/{movie_id}")
    Call<MovieDetailResponseDTO>
    getMovieDetail(@Path("movie_id") int movieId,
                   @Query("api_key") String apiKey,
                   @Query("language") String language,
                   @Query("append_to_response") String append);

}
