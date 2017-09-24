package br.com.marinho.thebestmoviesdb.infra;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import br.com.marinho.thebestmoviesdb.repository.API.RestClient;
import br.com.marinho.thebestmoviesdb.repository.LocalStoreManager;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;

/**
 * Created by Marinho on 21/09/17.
 */

public class App extends Application{
    private static App instance;
    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        restClient = RestClient.getInstance();
    }

    public static App getContext() {
        return instance;
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    public static void saveMovies(ArrayList<Movie> movieList) {
        Gson gson = new Gson();
        String movieStr = gson.toJson(movieList);

        new LocalStoreManager().put( LocalStoreManager.MOVIES_KEY, movieStr);
    }

    public static ArrayList<Movie> getMovies() {
        LocalStoreManager localStoreManager = new LocalStoreManager();

        String moviesLocal =  localStoreManager.get(LocalStoreManager.MOVIES_KEY, String.class);

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Movie>>(){}.getType();

        ArrayList<Movie> news = gson.fromJson(moviesLocal, type);

        return news;
    }




}
