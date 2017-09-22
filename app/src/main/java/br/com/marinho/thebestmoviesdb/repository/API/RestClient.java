package br.com.marinho.thebestmoviesdb.repository.API;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Marinho on 21/09/17.
 */

public class RestClient {
    private static RestClient instance = null;
    private Retrofit retrofit;

    private RestClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(APISettings.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                .build();
    }

    public static RestClient getInstance(){
        if(instance == null){
            instance = new RestClient();
        }
        return instance;
    }

    public <S> S createService(Class<S> serviceClass){
        return  retrofit.create(serviceClass);
    }

    public OkHttpClient initClient(){
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(getLogInterceptor())
                .build();
    }

    private static HttpLoggingInterceptor getLogInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    private class HeaderInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request());
        }
    }
}
