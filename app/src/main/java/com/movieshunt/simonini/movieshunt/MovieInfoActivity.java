package com.movieshunt.simonini.movieshunt;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieshunt.simonini.movieshunt.adapters.ReviewAdapter;
import com.movieshunt.simonini.movieshunt.adapters.TrailerAdapter;
import com.movieshunt.simonini.movieshunt.config.config;
import com.movieshunt.simonini.movieshunt.models.Movies;
import com.movieshunt.simonini.movieshunt.models.Review;
import com.movieshunt.simonini.movieshunt.models.ReviewList;
import com.movieshunt.simonini.movieshunt.models.Trailer;
import com.movieshunt.simonini.movieshunt.models.TrailerList;
import com.movieshunt.simonini.movieshunt.network.ApiClient;
import com.movieshunt.simonini.movieshunt.network.ApiInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MovieInfoActivity extends Activity implements TrailerAdapter.TrailerItemClickListener{

    // Add butterknife thanks to the advices of the reviewer.
    @BindView(R.id.iv_cover)
    ImageView backdrop;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.iv_poster)
    ImageView poster;
    @BindView(R.id.tv_year)
    TextView year;
    @BindView(R.id.tv_synopsis)
    TextView synopsis;
    @BindView(R.id.tv_ratings)
    TextView rating;

    /*
      ONCLICK
   */
    @Override
    public void onClick(int clickedPosition) {
        Context context = getApplicationContext();
        Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(this, MovieInfoActivity.class);

        //i.putExtra("MOVIE_INFO", movieData.get(clickedPosition));
        //startActivity(i);
    }


    public List<Review> reviewData;
    private ReviewAdapter mReviewAdapter;
    private static final int NUM_LIST_ITEMS = 100;
    public List<Trailer> trailerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Bundle b = getIntent().getExtras();
        Movies movie =
                b.getParcelable("MOVIE_INFO");

        // Movie ID
        int mId = movie.getId();
        String essay = String.valueOf(mId);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movies_components);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final RecyclerView recyclerViewTrailer = (RecyclerView) findViewById(R.id.rv_movies_trailer);
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));


        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);


        Call<ReviewList> call = apiService.getReviews(essay, config.API_KEY);
        call.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                List<Review> reviewsResult = response.body().getReviews();
                Log.v(TAG, "Reviews ok" + reviewsResult);

                recyclerView.setAdapter(new ReviewAdapter(reviewsResult, getApplicationContext()));


                /*if(reviewTV == null) {
                    reviewTV.setText("No Reviews");
                }*/

            }


            @Override
            public void onFailure(Call<ReviewList> call, Throwable error) {
                Log.e(TAG, "ERROR + " + error.toString());

            }
        });




        Call<TrailerList> call2 = apiService.getTrailers(essay, config.API_KEY);
        call2.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                List<Trailer> results = response.body().getTrailers();


                // recyclerViewTrailer.setAdapter(new TrailerAdapter(results, getApplicationContext(), TrailerAdapter.TrailerItemClickListener));


            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable error) {
                Log.e(TAG, "ERROR + " + error.toString());

            }
        });

        final Context context = this;
        ButterKnife.bind(this);
        /*
            Put information
         */


//        Toast.makeText(context, essay, Toast.LENGTH_SHORT).show();

        Picasso.with(context)
                .load(movie.getBackdrop())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(backdrop);
        title.setText(movie.getTitle());


        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(poster);

        year.setText(String.valueOf(movie.getReleaseDate()));


        synopsis.setText(movie.getSynopsis());


        // P2 : Change to RatingBar
        rating.setText("Rating : " + String.valueOf(movie.getVote()) + " /10");





    }
}



