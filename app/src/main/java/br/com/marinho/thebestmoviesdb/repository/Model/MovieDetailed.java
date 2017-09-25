package br.com.marinho.thebestmoviesdb.repository.Model;

import java.util.ArrayList;

import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDTO;

/**
 * Created by Marinho on 24/09/17.
 */

public class MovieDetailed{

    private String directorName;
    private ArrayList<String> castName;

    public MovieDetailed(String directorName, ArrayList<String> castName) {
        this.directorName = directorName;
        this.castName = castName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public ArrayList<String> getCastName() {
        return castName;
    }

    public void setCastName(ArrayList<String> castName) {
        this.castName = castName;
    }
}
