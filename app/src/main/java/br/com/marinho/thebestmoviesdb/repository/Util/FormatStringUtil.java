package br.com.marinho.thebestmoviesdb.repository.Util;

import br.com.marinho.thebestmoviesdb.repository.API.APISettings;

/**
 * Created by Marinho on 24/09/17.
 */

public class FormatStringUtil {
    public static String formatURLImage(String path, boolean largeImage){
        StringBuilder sb = new StringBuilder(APISettings.API_BASE_IMAGE);
        if(largeImage) {
            sb.append(APISettings.API_POSTER_IMAGE_LARGE);
        }else{
            sb.append(APISettings.API_POSTER_IMAGE_SMALL);
        }
        sb.append(path);
        return sb.toString();
    }

    public static String formatYearMovie(String date){
        if(date.length() >= 4) {
            return date.substring(0, 4);
        }

        return date;
    }
}
