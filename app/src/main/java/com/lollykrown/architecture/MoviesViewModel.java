package com.lollykrown.architecture;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lollykrown.architecture.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MoviesViewModel extends AndroidViewModel {
    private static final String TAG = MoviesViewModel.class.getSimpleName();
    String url = "https://api.themoviedb.org/3/movie/popular?api_key=0180207eb6ef9e35482bc3aa2a2b9672&language=en-US";

    private MoviesRepository mRepository;
    List<Movies> mMovies = new ArrayList<>();
    private LiveData<List<Movies>> mAllMovies;

    public MoviesViewModel(Application application) {
        super(application);
        mRepository = new MoviesRepository(application);
        mAllMovies = mRepository.getMovies();
        deleteAll();
        //if(mAllMovies == null) {
            loadData();
        //}
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "ViewModel destroyed");
    }

    LiveData<List<Movies>> getAllMovies() {
        return mAllMovies;
    }

    public void insertAll(List<Movies> m) {
        mRepository.insertAllMovies(m);
    }

    public void insert(Movies m) {
        mRepository.insertMovie(m);
    }
    public void update(Movies m) {
        mRepository.updateMovie(m);
    }
    public void delete(Movies m) {
        mRepository.deleteMovie(m);
    }

    public void deleteAll() {
        mRepository.deleteAllMovies();
    }

    private void loadData(){

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        // JsonArrayRequest req = new JsonArrayRequest(url,
        // new Response.Listener<JSONArray>() {

            JsonObjectRequest req = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString() + "Size: " + response.length());

                        try {
                            // Parsing json array response
                            // loop through each json object

                            JSONArray results = response.getJSONArray("results");

                            for (int i = 0; i < results.length(); i++) {

                                JSONObject movie = results.getJSONObject(i);

                                int movieId = movie.getInt("id");
                                String title = movie.getString("title");
                                String overview = movie.getString("overview");
                                String date = movie.getString("release_date");
                                double voteAverage = movie.getDouble("vote_average");
                                String posterPath = movie.getString("poster_path");
                                String backdropPath = movie.getString("backdrop_path");
                                Movies m = new Movies(movieId, title, overview, date, voteAverage, posterPath, backdropPath);
                                mMovies.add(m);
                            }

                            // txtResponse.setText(jsonResponse);
                            insertAll(mMovies);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplication(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplication(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 30000;
        RetryPolicy p = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        req.setRetryPolicy(p);

        // Adding request to request queue
        // AppController.getInstance().addToRequestQueue(req);
        requestQueue.add(req);
    }
}