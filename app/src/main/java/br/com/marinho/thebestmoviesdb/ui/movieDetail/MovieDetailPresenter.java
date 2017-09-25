package br.com.marinho.thebestmoviesdb.ui.movieDetail;

import br.com.marinho.thebestmoviesdb.repository.Model.MovieDetailed;
import br.com.marinho.thebestmoviesdb.repository.MovieRepository;
import br.com.marinho.thebestmoviesdb.repository.listeners.OnAPIListenerResult;
import br.com.marinho.thebestmoviesdb.ui.movieDetail.interfaces.IMovieDetailPresenter;
import br.com.marinho.thebestmoviesdb.ui.movieDetail.interfaces.IMovieDetailView;

/**
 * Created by Marinho on 24/09/17.
 */

public class MovieDetailPresenter implements IMovieDetailPresenter {

    private IMovieDetailView _view;

    public MovieDetailPresenter (IMovieDetailView view){
        _view = view;

        int movieId = _view.getMovieId();

        loadMovieDetail(movieId);
    }

    private void loadMovieDetail(int movieId){
        _view.showProgress();
        MovieRepository repository = new MovieRepository();
        repository.getDetailMovie(movieId, new OnAPIListenerResult<MovieDetailed>() {
            @Override
            public void onSuccessful(MovieDetailed value) {
                _view.loadDetail(value);
                _view.hideProgress();
            }

            @Override
            public void onUnsuccessful(String errorMsg) {
                _view.showError(errorMsg);
                _view.hideProgress();
            }

            @Override
            public void onUnexpectedError(String errorMsg) {
                _view.showError(errorMsg);
                _view.hideProgress();
            }
        });
    }

}
