package com.lollykrown.architecture.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movies {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "movieId")
    private int movieId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "overview")
    private String overview;

    @ColumnInfo(name = "movieDate")
    private String movieDate;

    @ColumnInfo(name = "voteAverage")
    private Double voteAverage;

    @ColumnInfo(name = "posterPath")
    private String posterPath;

    @ColumnInfo(name = "backdropPath")
    private String backdropPath;

    public Movies(int id, int movieId, String title, String overview, String movieDate, Double voteAverage, String posterPath, String backdropPath) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
        this.movieDate = movieDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public Movies(int movieId, String title, String overview, String movieDate, Double voteAverage, String posterPath, String backdropPath) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
        this.movieDate = movieDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    @Ignore
    public Movies(){
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOverview() {
        return overview;
    }
    public String getMovieDate() {
        return movieDate;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }
    public String getPosterPath() {
        return posterPath;
    }
    public String getBackdropPath() {
        return backdropPath;
    }
}