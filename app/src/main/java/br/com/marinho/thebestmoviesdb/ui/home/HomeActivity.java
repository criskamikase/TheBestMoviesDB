package br.com.marinho.thebestmoviesdb.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.ui.BaseView;

public class HomeActivity extends AppCompatActivity implements BaseView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showError(String code, String message) {

    }

    /**
     * Created by Marinho on 21/09/17.
     */

    public static interface HomeView extends BaseView{

    }
}
