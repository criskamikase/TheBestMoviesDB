package br.com.marinho.thebestmoviesdb.repository.API;

import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marinho on 21/09/17.
 */

public interface TheMovieDBApi {

    @GET("/movie/now_playing")
    Call<MovieResponseDTO<MovieDTO>> listMovies(@Query("api_key") String apiKey);
}
