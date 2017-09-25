package br.com.marinho.thebestmoviesdb.ui;

import android.content.Context;
import android.support.v7.widget.Toolbar;

/**
 * Created by Marinho on 25/09/17.
 */

public class ViewUtils {
    public static void setToolbarPadding(Toolbar toolbar, Context context) {
        int padding_in_dp = 95;  //95 dp
        int padding_in_px = convertDpToPx(context, padding_in_dp);

        toolbar.setPadding(padding_in_px , 0, 0, 0);
    }

    public static int convertDpToPx(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;

        int resultPx = (int) (dp * scale + 0.5f);

        return resultPx;
    }




}
