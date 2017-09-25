package br.com.marinho.thebestmoviesdb.repository.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marinho on 24/09/17.
 */

public class MovieDetailResponseDTO {
    @SerializedName("credits")
    private MovieDataDetailDTO credits;

    public class MovieDataDetailDTO{
        @SerializedName("cast")
        private ArrayList<CastDTO> cast;

        @SerializedName("crew")
        private ArrayList<CrewDTO> crew;

        public ArrayList<CastDTO> getCast() {
            return cast;
        }

        public ArrayList<CrewDTO> getCrew() {
            return crew;
        }
    }

    public MovieDataDetailDTO getCredits() {
        return credits;
    }
}
