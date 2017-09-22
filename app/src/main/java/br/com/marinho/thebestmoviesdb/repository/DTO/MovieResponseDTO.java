package br.com.marinho.thebestmoviesdb.repository.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Marinho on 21/09/17.
 */

public class MovieResponseDTO<T> {
    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<T> results;

    @SerializedName("dates")
    private T dates;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public T getDates() {
        return dates;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public ArrayList<T> getResults() {
        return results;
    }
}
