package br.com.marinho.thebestmoviesdb.ui.home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.ui.BaseView;
import br.com.marinho.thebestmoviesdb.ui.home.interfaces.IHomeView;

public class HomeActivity extends AppCompatActivity implements IHomeView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
