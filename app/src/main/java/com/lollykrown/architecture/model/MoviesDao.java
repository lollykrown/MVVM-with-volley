package com.lollykrown.architecture.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovies(Movies movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllMovies(List<Movies> movies);

    @Query("SELECT * FROM Movies ORDER BY title ASC")
    LiveData<List<Movies>> getAllMoviesLiveData();

    @Query("SELECT * FROM Movies ORDER BY title ASC")
    List<Movies> getAllMovies();
    @Query("SELECT * FROM Movies WHERE title LIKE :word ")
    List<Movies> searchMovies(String word);
    @Query("SELECT * FROM Movies WHERE movieId=:mid")
    List<Movies> getMovieById(String mid);

    @Update
    void updateMovie(Movies movies);

    @Query("DELETE FROM Movies")
    void deleteAllMovies();

    @Delete
    int deleteMovie(Movies movies);
}
