package com.movieshunt.simonini.movieshunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.movieshunt.simonini.movieshunt.adapters.MovieAdapter;
import com.movieshunt.simonini.movieshunt.config.config;
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

public class PosterActivity extends Activity implements MovieAdapter.PosterItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;
    private MovieAdapter adapter;

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);


        // Get a reference of our RecyclerView from xml :
        // It allows us the do things like set the Adapter of the RecyclerView and toggle the
        // visibility
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);

        // Don't change the size of the content
        //mRecyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);

        // Initialize new adapter
        adapter = new MovieAdapter(getApplicationContext(), NUM_LIST_ITEMS, new ArrayList<Movies>(), this);


        getPopularCall(mRecyclerView);

    }

    /*
       MENU
    */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final Context context = this;
        ButterKnife.bind(this);
        switch (item.getItemId()) {
            case R.id.favorite:
                //TODO: Implement the good function
                //faforite();
                return true;
            case R.id.topRated:
                getTopRatedCall(mRecyclerView);
                return true;
            case R.id.popular:
                getPopularCall(mRecyclerView);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
        CALL: get top rated movies
     */
    public void getTopRatedCall(RecyclerView mRecyclerView, final Callback<Movies> cb) {
        final RecyclerView recyclerView = mRecyclerView;
        final MovieAdapter.PosterItemClickListener listener = this;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesList> call = apiService.getTopRated(config.API_KEY, cb);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                final List<Movies> movies = response.body().getResults();

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
    public void getPopularCall(RecyclerView mRecyclerView, final Callback<Movies> cb) {
        final RecyclerView recyclerView = mRecyclerView;
        final MovieAdapter.PosterItemClickListener listener = this;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesList> call = apiService.getPopular(config.API_KEY, cb);
        call.enqueue(new Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                List<Movies> movies = response.body().getResults();
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
        i.putExtra("MOVIE_INFO", movies.get(clickedPosterIndex));
        startActivity(i);
        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
    }


}
