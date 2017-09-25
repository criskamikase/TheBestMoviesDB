package br.com.marinho.thebestmoviesdb.repository;

import java.util.ArrayList;
import java.util.List;

import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.repository.API.APISettings;
import br.com.marinho.thebestmoviesdb.repository.API.ErrorHelper;
import br.com.marinho.thebestmoviesdb.repository.API.TheMovieDBApi;
import br.com.marinho.thebestmoviesdb.repository.DTO.CastDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.CrewDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDetailResponseDTO;
import br.com.marinho.thebestmoviesdb.repository.DTO.MovieResponseDTO;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.repository.Model.MovieDetailed;
import br.com.marinho.thebestmoviesdb.repository.listeners.OnAPIListenerResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marinho on 21/09/17.
 */

public class MovieRepository {
    public void listMovies(int page, final OnAPIListenerResult<ArrayList<Movie>> listener){
        TheMovieDBApi theMovieDBApi = App.getRestClient().createService(TheMovieDBApi.class);

        theMovieDBApi.listMovies(APISettings.API_KEY, APISettings.API_LANGUAGE_KEY, page,
                APISettings.API_REGION_KEY).enqueue(new Callback<MovieResponseDTO<MovieDTO>>() {
            @Override
            public void onResponse(Call<MovieResponseDTO<MovieDTO>> call,
                                   Response<MovieResponseDTO<MovieDTO>> response) {
                if(response.isSuccessful() && response.body().getResults() != null){
                    ArrayList<Movie> listMovie = new ArrayList<Movie>();
                    for(MovieDTO mDTO : response.body().getResults()){
                        if(mDTO.getVoteAverage().doubleValue() > 5.0) {
                            listMovie.add(new Movie(mDTO));
                        }
                    }

                    listener.onSuccessful(listMovie);
                }else{
                    listener.onUnsuccessful(ErrorHelper.getError(response.errorBody()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponseDTO<MovieDTO>> call, Throwable t) {
                listener.onUnexpectedError(t.getLocalizedMessage());
            }
        });
    }

    public void getDetailMovie(int movieId, final OnAPIListenerResult<MovieDetailed> listener){
        TheMovieDBApi theMovieDBApi = App.getRestClient().createService(TheMovieDBApi.class);
        theMovieDBApi.getMovieDetail(movieId, APISettings.API_KEY,
                APISettings.API_LANGUAGE_KEY, APISettings.API_CREDITS_KEY).enqueue(
                        new Callback<MovieDetailResponseDTO>() {
            @Override
            public void onResponse(Call<MovieDetailResponseDTO> call,
                                   Response<MovieDetailResponseDTO> response) {
                if(response.isSuccessful() && response.body() != null){
                    ArrayList<String> castName = new ArrayList<String>();
                    String directorName = "";
                    for(CastDTO castDTO: response.body().getCredits().getCast()){
                        if(castDTO.getOrder() < 3){
                            castName.add(castDTO.getName());
                            if(castName.size() >= 3){
                                break;
                            }
                        }
                    }
                    for(CrewDTO crewDTO: response.body().getCredits().getCrew()){
                        if(crewDTO.getJob().equals("Director")){
                            directorName = crewDTO.getName();
                            break;
                        }
                    }
                    MovieDetailed movieDetailed = new MovieDetailed(directorName, castName);
                    listener.onSuccessful(movieDetailed);
                }else{
                    listener.onUnsuccessful(ErrorHelper.getError(response.errorBody()));
                }

            }

            @Override
            public void onFailure(Call<MovieDetailResponseDTO> call, Throwable t) {
                listener.onUnexpectedError(t.getLocalizedMessage());
            }
        });
    }
}
