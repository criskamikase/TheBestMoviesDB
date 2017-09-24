package br.com.marinho.thebestmoviesdb.ui;

/**
 * Created by Marinho on 21/09/17.
 */

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showError(String message);
}
