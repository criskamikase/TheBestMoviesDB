package br.com.marinho.thebestmoviesdb.ui.movieDetail;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.infra.DeviceHelper;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.repository.Model.MovieDetailed;
import br.com.marinho.thebestmoviesdb.repository.Util.FormatStringUtil;
import br.com.marinho.thebestmoviesdb.ui.Constants;
import br.com.marinho.thebestmoviesdb.ui.movieDetail.interfaces.IMovieDetailView;

/**
 * Created by Marinho on 24/09/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements IMovieDetailView {

    ImageView poster;
    TextView title;
    TextView subtitle;
    TextView year;
    TextView vote;
    TextView overview;
    TextView director;
    TextView cast;
    View parentView;

    ProgressDialog progressDialog;
    Movie movie;
    MovieDetailPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        if (!DeviceHelper.isNetworkAvailable(this)) {
            super.onBackPressed();
            Toast.makeText( this, R.string.noNetworkMsg, Toast.LENGTH_LONG ).show();
        }

        movie = getIntent().getParcelableExtra(Constants.MOVIE);

        setupView();

        setupToolbar();

        presenter = new MovieDetailPresenter(this);
    }

    private void setupView(){
        poster = (ImageView) findViewById(R.id.image_poster);
        title = (TextView) findViewById(R.id.text_title);
        subtitle = (TextView) findViewById(R.id.text_subtitle);
        year = (TextView) findViewById(R.id.text_year);
        vote = (TextView) findViewById(R.id.text_vote);
        overview = (TextView) findViewById(R.id.text_overview);
        director = (TextView) findViewById(R.id.text_director);
        cast = (TextView) findViewById(R.id.text_cast);

        Picasso.with(App.getContext()).load(FormatStringUtil.formatURLImage(movie.getPosterPath(),
                false)).into(poster);
        title.setText(movie.getTitle());
        subtitle.setText(movie.getOriginalTitle());
        year.setText(FormatStringUtil.formatYearMovie(movie.getReleaseDate()));
        vote.setText("Nota: " + String.valueOf(movie.getVoteAverage()));
        overview.setText(movie.getOverview());
    }

    @Override
    protected void onPause() {
        hideProgress();
        super.onPause();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.super.onBackPressed();
            }
        });

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        }
    }

    @Override
    public int getMovieId() {
        return movie.getId();
    }

    @Override
    public void loadDetail(MovieDetailed movieDetailed) {
        director.setText(movieDetailed.getDirectorName());
        if(movieDetailed.getCastName().size() > 0) {
            StringBuilder sb = new StringBuilder(movieDetailed.getCastName().get(0));
            for (int i = 1; i < movieDetailed.getCastName().size(); i++) {
                sb.append(", ");
                sb.append(movieDetailed.getCastName().get(i));
            }
            cast.setText(sb.toString());
        }
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this,
                getResources().getString(R.string.home_progress_title),
                getResources().getString(R.string.home_progress_message));
    }

    @Override
    public void hideProgress() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();
    }
}
