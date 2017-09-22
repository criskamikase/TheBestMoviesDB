package br.com.marinho.thebestmoviesdb.infra;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Marinho on 21/09/17.
 */

public class DeviceHelper {
    public static boolean isNetworkAvaliable(final Context context){
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
