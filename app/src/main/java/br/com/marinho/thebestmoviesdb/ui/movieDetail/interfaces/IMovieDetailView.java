package br.com.marinho.thebestmoviesdb.ui.movieDetail.interfaces;

import br.com.marinho.thebestmoviesdb.repository.Model.MovieDetailed;
import br.com.marinho.thebestmoviesdb.ui.BaseView;

/**
 * Created by Marinho on 24/09/17.
 */

public interface IMovieDetailView extends BaseView{
    void loadDetail(MovieDetailed movieDetailed);

    int getMovieId();
}
