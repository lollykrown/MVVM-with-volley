package com.lollykrown.architecture;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lollykrown.architecture.model.Movies;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    Context mContext;
    private final LayoutInflater mInflater;
    private List<Movies> mMovies; // Cached copy of movies

    class MoviesViewHolder extends RecyclerView.ViewHolder {
        TextView movieId;
        TextView title;
        TextView date;
        TextView voteAverage;
        ImageView posterPath;

        public MoviesViewHolder(final View itemView) {
            super(itemView);

//            movieId = itemView.findViewById(R.id.movie_id);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.year);
//            voteAverage = itemView.findViewById(R.id.vote_average);
            posterPath = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = getAdapterPosition();
                    Intent i = new Intent(itemView.getContext(), MovieDetailsActivity.class);

                    Movies m = mMovies.get(pos);

                    i.putExtra("title", m.getTitle());
                    i.putExtra("desc", m.getOverview());
                    i.putExtra("date", m.getMovieDate());
                    i.putExtra("vote", String.valueOf(m.getVoteAverage()));
                    i.putExtra("poster", m.getPosterPath());
                    i.putExtra("backdrop", m.getBackdropPath());
                    itemView.getContext().startActivity(i);
                }
            });
        }
    }

    MoviesAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.movie_list_item, parent, false);
        return new MoviesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {
        if (mMovies != null) {
            Movies m = mMovies.get(position);
//            holder.movieId.setText(String.valueOf(m.getMovieId()));
            holder.title.setText(m.getTitle());
//            holder.overview.setText(m.getOverview());
            holder.date.setText(m.getMovieDate());
//            holder.voteAverage.setText(String.valueOf(m.getVoteAverage()));
            String url = "https://image.tmdb.org/t/p/w500/" + m.getPosterPath();
            Glide.with(mContext)
                    .load(url)
                    .error(R.drawable.profilepic)
                    .placeholder(R.drawable.profilepic)
                    .into(holder.posterPath);
        } else {
            // Covers the case of data not being ready yet.
            // holder.progress.setVisibility(View.VISIBLE);
        }
    }

    void setMovies(List<Movies> movies){
        mMovies = movies;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mMovies != null)
            return mMovies.size();
        else return 0;
    }
}
