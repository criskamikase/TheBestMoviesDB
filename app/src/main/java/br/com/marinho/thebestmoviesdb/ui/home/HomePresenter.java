package br.com.marinho.thebestmoviesdb.ui.home;

import java.util.ArrayList;
import java.util.List;

import br.com.marinho.thebestmoviesdb.infra.App;
import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.repository.MovieRepository;
import br.com.marinho.thebestmoviesdb.repository.listeners.OnAPIListenerResult;
import br.com.marinho.thebestmoviesdb.ui.home.interfaces.IHomePresenter;
import br.com.marinho.thebestmoviesdb.ui.home.interfaces.IHomeView;

/**
 * Created by Marinho on 21/09/17.
 */

public class HomePresenter implements IHomePresenter{
    public static final int FIRST_PAGE = 1;

    private IHomeView _view;

    public HomePresenter(IHomeView view){
        _view = view;
    }

    @Override
    public void loadMovie(final int page){
        if(page == FIRST_PAGE){
            _view.showProgress();
        }
        new MovieRepository().listMovies(page, new OnAPIListenerResult<ArrayList<Movie>>() {
            @Override
            public void onSuccessful(ArrayList<Movie> value) {
                if(page == FIRST_PAGE) {
                    App.saveMovies(value);
                }
                _view.loadMovies(value);
                _view.hideProgress();
            }

            @Override
            public void onUnsuccessful(String errorMsg) {
                _view.showError( errorMsg );
                _view.hideProgress();

            }

            @Override
            public void onUnexpectedError(String errorMsg) {
                _view.showError( errorMsg );
                _view.hideProgress();
            }
        });
    }

    @Override
    public void searchMovie(String query, int page) {
        if(page == FIRST_PAGE){
            _view.showProgress();
        }
        new MovieRepository().searchMovie(query, page, new OnAPIListenerResult<ArrayList<Movie>>() {
            @Override
            public void onSuccessful(ArrayList<Movie> value) {
                _view.loadMovies(value);
                _view.hideProgress();
            }

            @Override
            public void onUnsuccessful(String errorMsg) {
                _view.showError( errorMsg );
                _view.hideProgress();
            }

            @Override
            public void onUnexpectedError(String errorMsg) {
                _view.showError( errorMsg );
                _view.hideProgress();
            }
        });
    }
}
