package com.movieshunt.simonini.movieshunt;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.movieshunt.simonini.movieshunt.adapters.MovieAdapter;
import com.movieshunt.simonini.movieshunt.adapters.MovieCursorAdapter;
import com.movieshunt.simonini.movieshunt.config.config;
import com.movieshunt.simonini.movieshunt.data.MoviesContract;
import com.movieshunt.simonini.movieshunt.models.Movies;
import com.movieshunt.simonini.movieshunt.models.MoviesList;
import com.movieshunt.simonini.movieshunt.network.ApiClient;
import com.movieshunt.simonini.movieshunt.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PosterActivity extends AppCompatActivity implements MovieAdapter.PosterItemClickListener,
        LoaderManager.LoaderCallbacks<Cursor>, MovieCursorAdapter.PosterCursorItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;
    @BindView(R.id.no_data)
    ConstraintLayout mNoData;

    ArrayList<Movies> moviesArrayList;

    private MovieCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final MovieAdapter.PosterItemClickListener listener = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        Context context = getApplicationContext();

        // Verify that we have an internet connection
        ConnectionStatusUtils.verifyConnection(context, findViewById(R.id.coordinatorLayout));

        // Get a reference of our RecyclerView from xml :
        // It allows us the do things like set the Adapter of the RecyclerView and toggle the
        // visibility
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        moviesArrayList = new ArrayList<>();

        ButterKnife.bind(this);

        getTopRatedCall(mRecyclerView, mNoData);

        mNoData.setVisibility(View.GONE);

        final MovieCursorAdapter.PosterCursorItemClickListener listenerCursor = this;
    }

    /*
       MENU
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        ButterKnife.bind(this);
        switch (item.getItemId()) {
            case R.id.favorite:
                getFavorites(mRecyclerView);
                return true;
            case R.id.topRated:
                getTopRatedCall(mRecyclerView, mNoData);
                return true;
            case R.id.popular:
                getPopularCall(mRecyclerView, mNoData);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        CALL: get favorites
     */
    public void getFavorites(RecyclerView mRecyclerView) {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    /*
        CALL: get top rated movies
     */
    public void getTopRatedCall(RecyclerView mRecyclerView, ConstraintLayout mNoData) {
        final RecyclerView recyclerView = mRecyclerView;
        final MovieAdapter.PosterItemClickListener listener = this;

        mRecyclerView.setVisibility(View.VISIBLE);
        mNoData.setVisibility(View.GONE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesList> call = apiService.getTopRated(config.API_KEY);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                final List<Movies> movies = response.body().getResults();
                moviesArrayList.clear();
                List<Movies> moviesResult = movies;
                for (Movies singleMovie : response.body().getResults()) {
                    moviesArrayList.add(singleMovie);
                }
                recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), NUM_LIST_ITEMS, movies, listener));
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable error) {
                // Log error here since request failed
                Log.e("ERROR", error.toString());
            }
        });
    }

    /*
       CALL: get popular
    */
    public void getPopularCall(RecyclerView mRecyclerView, ConstraintLayout mNoData) {
        final RecyclerView recyclerView = mRecyclerView;
        final MovieAdapter.PosterItemClickListener listener = this;

        mRecyclerView.setVisibility(View.VISIBLE);
        mNoData.setVisibility(View.GONE);

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesList> call = apiService.getPopular(config.API_KEY);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                final List<Movies> movies = response.body().getResults();
                moviesArrayList.clear();
                List<Movies> moviesResult = movies;
                for (Movies singleMovie : response.body().getResults()) {
                    moviesArrayList.add(singleMovie);
                }
                recyclerView.setAdapter(new MovieAdapter(getApplicationContext(), NUM_LIST_ITEMS, movies, listener));
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable error) {
                // Log error here since request failed
                Log.e("ERROR", error.toString());
            }
        });
    }

    @Override
    public void onPosterItemClick(int clickedPosterIndex) {
        Context context = getApplicationContext();
        Intent i = new Intent(this, MovieInfoActivity.class);
        i.putExtra("MOVIE_INFO", moviesArrayList.get(clickedPosterIndex));
        startActivity(i);
    }

    @Override
    public void onPosterItemCursorClick(int clickedCursorPosterIndex) {
        Context context = getApplicationContext();
        Intent i = new Intent(this, MovieInfoActivity.class);
        i.putExtra("MOVIE_INFO", moviesArrayList.get(clickedCursorPosterIndex));
        startActivity(i);
    }

    @Override
    public void onResume() {
        Context context = getApplicationContext();
        super.onResume();  // Always call the superclass method first

        // Verify that we have an internet connection
        ConnectionStatusUtils.verifyConnection(context, findViewById(R.id.coordinatorLayout));
    }


    // FAVORITES LOADER
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MoviesContract.FavoriteEntry.CONTENT_URI,
                null, "isFavorite = 1", null, null);


    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor favoriteCursor) {
        MovieCursorAdapter.PosterCursorItemClickListener listenerCursor = null;
        listenerCursor = this;
        if (favoriteCursor.moveToFirst()) {
            // create adapter
            moviesArrayList.clear();
            favoriteCursor.moveToFirst();
            Movies movie = new Movies(favoriteCursor.getInt(favoriteCursor.getColumnIndex("movie_id")),
                    favoriteCursor.getString(favoriteCursor.getColumnIndex("title")),
                    favoriteCursor.getString(favoriteCursor.getColumnIndex("poster_path")),
                    favoriteCursor.getString(favoriteCursor.getColumnIndex("backdrop_path")),
                    favoriteCursor.getString(favoriteCursor.getColumnIndex("release_date")),
                    favoriteCursor.getDouble(favoriteCursor.getColumnIndex("vote_average")),
                    favoriteCursor.getString(favoriteCursor.getColumnIndex("overview")));
            moviesArrayList.add(movie);

            while (favoriteCursor.moveToNext()) {
                movie = new Movies(favoriteCursor.getInt(favoriteCursor.getColumnIndex("movie_id")),
                        favoriteCursor.getString(favoriteCursor.getColumnIndex("title")),
                        favoriteCursor.getString(favoriteCursor.getColumnIndex("poster_path")),
                        favoriteCursor.getString(favoriteCursor.getColumnIndex("backdrop_path")),
                        favoriteCursor.getString(favoriteCursor.getColumnIndex("release_date")),
                        favoriteCursor.getDouble(favoriteCursor.getColumnIndex("vote_average")),
                        favoriteCursor.getString(favoriteCursor.getColumnIndex("overview")));
                moviesArrayList.add(movie);
            }
            this.mAdapter = new MovieCursorAdapter(getApplicationContext(), favoriteCursor, listenerCursor);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            mRecyclerView.setVisibility(View.GONE);
            mNoData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        //mAdapter.swapCursor(null);
    }
}
