package com.movieshunt.simonini.movieshunt;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.movieshunt.simonini.movieshunt.adapters.ReviewAdapter;
import com.movieshunt.simonini.movieshunt.adapters.TrailerAdapter;
import com.movieshunt.simonini.movieshunt.config.config;
import com.movieshunt.simonini.movieshunt.data.MoviesContract;
import com.movieshunt.simonini.movieshunt.models.Movies;
import com.movieshunt.simonini.movieshunt.models.Review;
import com.movieshunt.simonini.movieshunt.models.ReviewList;
import com.movieshunt.simonini.movieshunt.models.Trailer;
import com.movieshunt.simonini.movieshunt.models.TrailerList;
import com.movieshunt.simonini.movieshunt.network.ApiClient;
import com.movieshunt.simonini.movieshunt.network.ApiInterface;
import com.squareup.picasso.Picasso;
import com.movieshunt.simonini.movieshunt.data.MoviesContract.FavoriteEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MovieInfoActivity extends AppCompatActivity implements TrailerAdapter.TrailerItemClickListener {

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
    @BindView(R.id.bv_favorite)
    FloatingActionButton favorite;
    @BindView(R.id.bv_already_favorite)
    FloatingActionButton alreadyFavorite;

    ArrayList<Trailer> trailerArrayList;
    public List<Review> reviewData;
    private ReviewAdapter mReviewAdapter;
    private static final int NUM_LIST_ITEMS = 100;
    public List<Trailer> trailerData;
    private Movies movieSelected;
    ShareActionProvider mShareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        final TrailerAdapter.TrailerItemClickListener listener = this;

        updateFavoriteButton();

        trailerArrayList = new ArrayList<>();

//        String hello = trailerArrayList.get(0).toString();

        Bundle b = getIntent().getExtras();
        final Movies movieSelected =
                b.getParcelable("MOVIE_INFO");

        // Movie ID
        final int mId = movieSelected.getId();
        String essay = String.valueOf(mId);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_movies_components);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final RecyclerView recyclerViewTrailer = (RecyclerView) findViewById(R.id.rv_movies_trailer);
        recyclerViewTrailer.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final Context context = this;
        ButterKnife.bind(this);

        favorite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToFavorite(v, mId, movieSelected);
                    }
                });

        alreadyFavorite.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteFavorite(movieSelected);
                    }
                });

        /*
            GET REVIEWS
         */
        Call<ReviewList> call = apiService.getReviews(essay, config.API_KEY);
        call.enqueue(new Callback<ReviewList>() {

            @Override
            public void onResponse(Call<ReviewList> call, Response<ReviewList> response) {
                List<Review> reviewsResult = response.body().getReviews();
                recyclerView.setAdapter(new ReviewAdapter(reviewsResult, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<ReviewList> call, Throwable error) {
                Log.e(TAG, "ERROR + " + error.toString());
            }
        });

        /*
            GET TRAILERS
         */
        Call<TrailerList> call2 = apiService.getTrailers(essay, config.API_KEY);
        call2.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                List<Trailer> results = response.body().getTrailers();
                recyclerViewTrailer.setAdapter(new TrailerAdapter(getApplicationContext(), NUM_LIST_ITEMS, results, listener));

                for (Trailer singleTrailer : response.body().getTrailers()) {
                    trailerArrayList.add(singleTrailer);
                }
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable error) {
                Log.e(TAG, "ERROR + " + error.toString());
            }
        });


        /*
            Bind information
         */
        Picasso.with(context)
                .load(movieSelected.getBackdrop())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(backdrop);
        title.setText(movieSelected.getTitle());

        Picasso.with(context)
                .load(movieSelected.getPoster())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(poster);

        year.setText(String.valueOf(movieSelected.getReleaseDate()));
        synopsis.setText(movieSelected.getSynopsis());
        rating.setText("Rating : " + String.valueOf(movieSelected.getVote()) + " /10");
    }

    /*
        MENU
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.moreinfo_menu, menu);
// Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.action_share);
// Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(createShareIntent());



        return true;
    }
    // Call to update the share intent
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");

        return shareIntent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        ButterKnife.bind(this);
        switch (item.getItemId()) {
            case R.id.action_share:
                createShareIntent();
                Toast.makeText(this, "Do we ?", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        SHARE INTENT
     */



    @Override
    public void onTrailerItemClick(int clickedTrailerIndex) {
        Context context = getApplicationContext();
        String key = trailerArrayList.get(clickedTrailerIndex).getKey();
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(key)));
    }

    // ON CLICK FAVORITE (AsyncTask because asynchronous)
    public void addToFavorite(View view, int id, Movies mMovieSelected) {
        // Get Movie's id
        final int movieId = id;
        final Movies movieSelected = mMovieSelected;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                // Done Implement a if statement already favorite
                if (!isAlreadyFavorite()) {
                    // Insert new task data via a ContentResolver
                    ContentValues movieValues = new ContentValues();
                    movieValues.put(FavoriteEntry.COLUMN_ID, movieId);
                    movieValues.put(FavoriteEntry.COLUMN_COVER, movieSelected.getBackdrop());
                    movieValues.put(FavoriteEntry.COLUMN_DATE, movieSelected.getReleaseDate());
                    movieValues.put(FavoriteEntry.COLUMN_POSTER_PATH, movieSelected.getPoster());
                    movieValues.put(FavoriteEntry.COLUMN_RATING, movieSelected.getVote());
                    movieValues.put(FavoriteEntry.COLUMN_SYNOPSIS, movieSelected.getSynopsis());
                    movieValues.put(FavoriteEntry.COLUMN_TITLE, movieSelected.getTitle());
                    movieValues.put(FavoriteEntry.COLUMN_POPULAR, 0);
                    movieValues.put(FavoriteEntry.COLUMN_TOP_RATED, 0);
                    movieValues.put(FavoriteEntry.COLUMN_FAVORITE, 1);

                    // Insert the content values via a ContentResolver
                    Uri uri = getContentResolver().insert(MoviesContract.FavoriteEntry.CONTENT_URI, movieValues);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFavoriteButton();
            }
        }.execute();
    }

    // DELETE FROM FAVORITE
    public void deleteFavorite(Movies mMovieSelected) {
        // Get Movie's id
        final Movies movieSelected = mMovieSelected;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                if (isAlreadyFavorite()) {
                    getApplicationContext().getContentResolver().delete(FavoriteEntry.CONTENT_URI,
                            FavoriteEntry.COLUMN_ID + " = " + movieSelected.getId(), null);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                updateFavoriteButton();
            }
        }.execute();
    }

    /*
       isAlreadyFavorite()
       Thanks for the advice to @Dimitry Malovitch
    */
    private boolean isAlreadyFavorite() {
        Bundle b = getIntent().getExtras();
        Movies movieSelected =
                b.getParcelable("MOVIE_INFO");

        Cursor favoriteCursor = getApplicationContext().getContentResolver().query(
                FavoriteEntry.CONTENT_URI,
                new String[]{FavoriteEntry.COLUMN_ID},
                FavoriteEntry.COLUMN_ID + " = " + movieSelected.getId(),
                null,
                null);

        // If found
        if (favoriteCursor != null && favoriteCursor.moveToFirst()) {
            favoriteCursor.close();
            return true;
        } else {
            return false;
        }

    }


    /*
        Update our favorite button:
        - Bookmark full when added to favorites
        - Bookmark empty when not in favorites
        Thanks for the advice use an AsyncTask to update the UI @Dimitry Malovitch
     */
    public void updateFavoriteButton() {
        Log.v("Update", "UpdateFavorite button");
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return isAlreadyFavorite();
            }

            @Override
            protected void onPostExecute(Boolean isAlreadyFavorite) {
                if (!isAlreadyFavorite()) {
                    favorite.setVisibility(View.VISIBLE);
                    alreadyFavorite.setVisibility(View.GONE);
                } else {
                    favorite.setVisibility(View.GONE);
                    alreadyFavorite.setVisibility(View.VISIBLE);
                }
            }


        }.execute();
    }
}






