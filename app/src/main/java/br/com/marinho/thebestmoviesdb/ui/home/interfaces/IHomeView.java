package br.com.marinho.thebestmoviesdb.ui.home.interfaces;

import java.util.ArrayList;

import br.com.marinho.thebestmoviesdb.repository.Model.Movie;
import br.com.marinho.thebestmoviesdb.ui.BaseView;

/**
 * Created by Marinho on 22/09/17.
 */

public interface IHomeView extends BaseView {
    void loadMovies(ArrayList<Movie> movies);

}
