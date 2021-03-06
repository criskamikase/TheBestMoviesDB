package br.com.marinho.thebestmoviesdb.repository;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.marinho.thebestmoviesdb.infra.App;

/**
 * Created by Marinho on 23/09/17.
 */

public class LocalStoreManager{
    public static final String MOVIES_KEY = "movies";

    private static final String SP_NAME = "SHAREDPREFERENCES_THEBESTMOVIESDB";
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public LocalStoreManager()
    {
        sharedPreferences = App.getContext().getSharedPreferences( SP_NAME, Context.MODE_PRIVATE );
        gson = new Gson();
    }

    public <T> T get(String key, Class<T> clazz) {
        return gson.fromJson(sharedPreferences.getString(key, null), clazz);
    }

    public <T> void put(String key, T value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, gson.toJson(value));
        editor.apply();
    }

    public boolean contain(String key)
    {
        return sharedPreferences.contains(key);
    }

    public void delete(String key)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }
}



