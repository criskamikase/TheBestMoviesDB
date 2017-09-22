package br.com.marinho.thebestmoviesdb.repository.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Marinho on 21/09/17.
 */

public class ResponseErrorDTO {
    @SerializedName("status_message")
    private String statusMessage;

    @SerializedName("status_code")
    private int statusCode;

    public String getStatusMessage() {
        return statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
