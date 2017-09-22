package br.com.marinho.thebestmoviesdb.repository.API;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import br.com.marinho.thebestmoviesdb.repository.DTO.ResponseErrorDTO;
import okhttp3.ResponseBody;

/**
 * Created by Marinho on 21/09/17.
 */

public class ErrorHelper {
    public static String getError(ResponseBody errorBody){
        Gson gson = new Gson();
        ResponseErrorDTO responseErrorDTO = null;

        try{
            String json = errorBody.string();
            responseErrorDTO = gson.fromJson(json, ResponseErrorDTO.class);
        }catch (Exception e){
            Log.e("ErrorHelper", e.getLocalizedMessage());
        }

        if(responseErrorDTO!= null){
            return responseErrorDTO.getStatusMessage();
        }

        return null;
    }
}
