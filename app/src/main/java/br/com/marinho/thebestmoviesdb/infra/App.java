package br.com.marinho.thebestmoviesdb.infra;

import android.app.Application;

import br.com.marinho.thebestmoviesdb.repository.API.RestClient;

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
}
