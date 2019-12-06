package com.lollykrown.architecture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView backdrop;
    private ImageView poster;
    private TextView title;
    private TextView yr;
    private TextView ratings;
    private TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        backdrop = findViewById(R.id.backdrop);
        poster = findViewById(R.id.poster);
        title = findViewById(R.id.movieTitle);
        yr = findViewById(R.id.movieYear);
        ratings = findViewById(R.id.ratings);
        desc = findViewById(R.id.description);

        Intent i = getIntent();

        String mTitle = i.getStringExtra("title");
        String mDate = i.getStringExtra("date");
        String mDesc = i.getStringExtra("desc");
        String mRatings = i.getStringExtra("vote");
        String mPoster = i.getStringExtra("poster");
        String mBackdrop = i.getStringExtra("backdrop");

        String posterUri = "https://image.tmdb.org/t/p/w500/" + mPoster;
        String backdropUri = "https://image.tmdb.org/t/p/w500/" + mBackdrop;

        Glide.with(this)
                .load(backdropUri)
                .asBitmap()
                .error(R.drawable.profilepic)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(backdrop);

        Glide.with(this)
                .load(posterUri)
                .asBitmap()
                .error(R.drawable.profilepic)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(poster);
        //eventImgTv.setTransitionName("thumbnailTransition");

        title.setText(mTitle);
        yr.setText(mDate);
        desc.setText(mDesc);
        ratings.setText(String.valueOf(mRatings));

    }
}
