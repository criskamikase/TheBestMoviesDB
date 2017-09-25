package br.com.marinho.thebestmoviesdb.repository.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import br.com.marinho.thebestmoviesdb.repository.DTO.MovieDTO;

/**
 * Created by Marinho on 21/09/17.
 */

public class Movie implements Parcelable{
    private String posterPath;

    private boolean adult;

    private String overview;

    private String releaseDate;

    private int id;

    private String originalTitle;

    private String originalLanguage;

    private String title;

    private double popularity;

    private int voteCount;

    private double voteAverage;

    public Movie(Movie movie) {
        this.posterPath = movie.getPosterPath();
        this.adult = movie.isAdult();
        this.overview = movie.getOverview();
        this.releaseDate = movie.getReleaseDate();
        this.id = movie.getId();
        this.originalTitle = movie.getOriginalTitle();
        this.originalLanguage = movie.getOriginalLanguage();
        this.title = movie.getTitle();
        this.popularity = movie.getPopularity();
        this.voteCount = movie.getVoteCount();
        this.voteAverage = movie.getVoteAverage();
    }

    public Movie(MovieDTO md) {
        this.posterPath = md.getPosterPath();
        this.adult = md.isAdult();
        this.overview = md.getOverview();
        this.releaseDate = md.getReleaseDate();
        this.id = md.getId();
        this.originalTitle = md.getOriginalTitle();
        this.originalLanguage = md.getOriginalLanguage();
        this.title = md.getTitle();
        this.popularity = md.getPopularity().doubleValue();
        this.voteCount = md.getVoteCount();
        this.voteAverage = md.getVoteAverage().doubleValue();
    }

    public Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.id = in.readInt();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.popularity = in.readDouble();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(posterPath);
        dest.writeByte((byte)(adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
