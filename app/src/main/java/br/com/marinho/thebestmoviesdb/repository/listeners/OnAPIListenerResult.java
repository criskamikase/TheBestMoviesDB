package br.com.marinho.thebestmoviesdb.repository.listeners;

/**
 * Created by Marinho on 21/09/17.
 */

public interface OnAPIListenerResult<T> {
    void onSuccessful(T value);
    void onUnsuccessful(String errorMsg);
    void onUnexpectedError(String errorMsg);
}
