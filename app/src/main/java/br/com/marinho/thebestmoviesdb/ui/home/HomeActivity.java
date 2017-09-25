package br.com.marinho.thebestmoviesdb.ui.home;

import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import br.com.marinho.thebestmoviesdb.R;
import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.infra.DeviceHelper;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.ui.BaseView;
import br.com.marinho.thebestmoviesdb.ui.home.adapter.HomeAdapter;
import br.com.marinho.thebestmoviesdb.ui.home.interfaces.IHomeView;

public class HomeActivity extends AppCompatActivity implements IHomeView{

    static RecyclerView recyclerViewMovie;
    LinearLayoutManager layoutManager;
    View parentView;
    ArrayList<Movie> listMovie;
    int position = 0;
    HomeAdapter adapterMovie;
    boolean addMoreItems = false;
    boolean isLoading = false;
    boolean endItems = false;
    int page = HomePresenter.FIRST_PAGE;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeContainer;

    HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();

        setupSwipeView();
        swipeContainer.setRefreshing(true);

        if (App.getMovies() != null) {
            fillMovieList(App.getMovies());
        }else {
            loadMovie();
        }
    }

    private void loadMovie(){
        if (DeviceHelper.isNetworkAvailable(App.getContext())) {
            homePresenter.loadMovie(page);
        } else {
            Snackbar.make(parentView, getString(R.string.noNetworOfflinekMsg),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void setupView(){
        recyclerViewMovie = (RecyclerView) findViewById(R.id.recyclerMovie);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        parentView = findViewById(R.id.parentLayout);

        homePresenter = new HomePresenter(this);
    }

    public void setupSwipeView() {
        //position = 0;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                scrollToTop();
                swipeContainer.setRefreshing(true);
                page = HomePresenter.FIRST_PAGE;
                listMovie = new ArrayList<>();
                loadMovie();
                Log.d("SwipeRefresh", "Refreshing");
            }
        });

        layoutManager = new LinearLayoutManager(App.getContext());
        recyclerViewMovie.setLayoutManager(layoutManager);
        listMovie = new ArrayList<>();

        recyclerViewMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int total = layoutManager.getItemCount();
                int firstVisibleItemCount = layoutManager.findFirstVisibleItemPosition();
                int lastVisibleItemCount = layoutManager.findLastVisibleItemPosition();
                isLoading = swipeContainer.isRefreshing();

                if (!isLoading && !endItems) {
                    if (total > 0) {
                        if ((total - 1) == lastVisibleItemCount) {
                            if (DeviceHelper.isNetworkAvailable(App.getContext())) {
                                Log.i("final", "total " + total + "lastVisibleItemCount " +
                                        lastVisibleItemCount + "page: " + page);
                                page++;
                                addMoreItems = true;

                                swipeContainer.setRefreshing(true);
                                homePresenter.loadMovie(page);
                            } else {
                                Snackbar.make(parentView, getString(R.string.noNetworOfflinekMsg),
                                        Snackbar.LENGTH_LONG).show();

                                if (App.getMovies() != null) {
                                    fillMovieList(App.getMovies());
                                }
                            }
                        } else if (firstVisibleItemCount == (listMovie.size() - total)) {
                            Log.i("final", "firstVisibleItemCount" + firstVisibleItemCount);
                        }
                    }
                }
            }
        });
    }

    public static void scrollToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager)
                recyclerViewMovie.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(0, 0);
    }

    private void fillMovieList(ArrayList<Movie> movieList) {
        swipeContainer.setRefreshing(false);

        if (movieList.size() != 0) {
            if (addMoreItems) {
                position = listMovie.size();
                listMovie.addAll(position, movieList);

                adapterMovie = new HomeAdapter(listMovie, parentView, this);
                recyclerViewMovie.setAdapter(adapterMovie);
                adapterMovie.notifyDataSetChanged();
                layoutManager.scrollToPosition(position);
            } else {
                listMovie.addAll(position, movieList);

                adapterMovie = new HomeAdapter(listMovie, parentView, this);
                recyclerViewMovie.setAdapter(adapterMovie);
            }
        } else {
            endItems = true;
        }
    }

    @Override
    public void loadMovies(ArrayList<Movie> movies) {
        fillMovieList(movies);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this,
                getResources().getString(R.string.home_progress_title),
                getResources().getString(R.string.home_progress_message));
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();
    }
}
