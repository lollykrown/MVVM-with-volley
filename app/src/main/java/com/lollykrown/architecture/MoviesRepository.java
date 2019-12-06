package com.lollykrown.architecture;

import android.app.Application;
import android.os.AsyncTask;

import com.lollykrown.architecture.model.Movies;
import com.lollykrown.architecture.model.MoviesDao;
import com.lollykrown.architecture.model.MoviesDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MoviesRepository {

    private MoviesDao mMoviesDao;
    private LiveData<List<Movies>> mAllMovies;

    MoviesRepository(Application application) {
        MoviesDatabase db = MoviesDatabase.getDatabase(application);
        mMoviesDao = db.moviesDao();
        mAllMovies = mMoviesDao.getAllMoviesLiveData();
    }

        // Room executes all queries on a separate thread.
        // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Movies>> getMovies() {
        return mAllMovies;
    }

    public void insertMovie(Movies movie) {
        new InsertMovieAsyncTask(mMoviesDao).execute(movie);
    }

    public void insertAllMovies(List<Movies> movie) {
        new InsertAllMoviesAsyncTask(mMoviesDao).execute(movie);
    }

    public void updateMovie(Movies movie) {
        new UpdateMovieAsyncTask(mMoviesDao).execute(movie);
    }

    public void deleteMovie(Movies movie) {
        new DeleteMovieAsyncTask(mMoviesDao).execute(movie);
    }

    public void deleteAllMovies() {
        new DeleteAllMovieAsyncTask(mMoviesDao).execute();
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Movies, Void, Void> {
        private MoviesDao movieDao;

        private InsertMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movies... movies) {
            movieDao.addMovies(movies[0]);
            return null;
        }
    }

    private static class InsertAllMoviesAsyncTask extends AsyncTask<List<Movies>, Void, Void> {
        private MoviesDao movieDao;

        private InsertAllMoviesAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(List<Movies>... movies) {
            movieDao.addAllMovies(movies[0]);
            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Movies, Void, Void> {
        private MoviesDao movieDao;

        private UpdateMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movies... movies) {
            movieDao.updateMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Movies, Void, Void> {
        private MoviesDao movieDao;

        private DeleteMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Movies... movies) {
            movieDao.deleteMovie(movies[0]);
            return null;
        }
    }

    private static class DeleteAllMovieAsyncTask extends AsyncTask<Void, Void, Void> {
        private MoviesDao movieDao;

        private DeleteAllMovieAsyncTask(MoviesDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.deleteAllMovies();
            return null;
        }
    }

}
