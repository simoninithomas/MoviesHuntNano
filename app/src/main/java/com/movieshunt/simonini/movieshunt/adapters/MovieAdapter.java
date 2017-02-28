package com.movieshunt.simonini.movieshunt.adapters;


import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.movieshunt.simonini.movieshunt.R;
import com.movieshunt.simonini.movieshunt.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    //private final PosterItemClickListener mOnClickListener;
    private Context context;
    private List<Movies> mMovies;


    /*
        RESPONDING TO CLICK ITEMS

    // Step 1: Create an interface that will define our listener
    public interface PosterItemClickListener {
        // Method that takes an int as parameter
        void onClick(int clickedItemIndex);

    } */

    /*
        VIEW HOLDER
     */
    // ViewHolders cache the references to the views that will be modified in the adapter.
    // We create a class called MovieViewHolder that extends Recycler.ViewHolder
    public class MovieViewHolder extends RecyclerView.ViewHolder
    {
        //implements View.OnClickListener
        // Create a ImageView variable called posterView
        ImageView posterView;

        // Create a constructor for MovieViewHolder that accepts a View called itemView as a parameter

        public MovieViewHolder(View itemView) {
            super(itemView);

            // listItemNumberView = (TextView) itemView.findViewById(R.id.tv_title);
            posterView = (ImageView) itemView.findViewById(R.id.tv_poster);
            // Call setOnClickListener on the View passed into the constructor
            // (use 'this' as the OnClickListener)
            //itemView.setOnClickListener(this);
            Log.v("Call on movie viewhol", "");
        }

        /* Override onClickMethod
        @Override
        public void onClick(View view) {
            // Get the adapter position (item that was selected)
            int clickedPosition = getAdapterPosition();
            //mOnClickListener.onClick(clickedPosition);

            //Toast.makeText(context, arr[1], Toast.LENGTH_SHORT).show();
        }*/
    }

    /*
        ADAPTER
     */
    // Specify how many views adapter hold
    private int mNumberItems;

    // Store a member variable for the titles


    // Populate that var in the constructor
    //PosterItemClickListener listener
    public MovieAdapter(Context context, int numberOfItems, ArrayList<Movies> movies) {
        mNumberItems = numberOfItems;
        mMovies = movies;
        //mOnClickListener = listener;
        this.context = context;

    }

    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.v("onCreateViewHolder", "onCreateViewHolder is called !");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.poster;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;


    }


    //onBindViewHolder()
    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        Log.v("We inflate ?", "Seems yes");

        // Get the data model based on position

        Movies movie = mMovies.get(position);

        // Set item views based on your views and data model
        //TextView textView = holder.listItemNumberView;
        //textView.setText(movie.getMovieTitle());


        // Set item views based on your views and data model
        ImageView imageView = holder.posterView;

        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(imageView);

    }

    //getItemCount() : returns the mNumberItems var
    @Override
    public int getItemCount() {
        mNumberItems = mMovies.size();
        return mNumberItems;
    }

    //


    /**
     * This method is used to set the movie data on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param movieData The new moviesData data to be displayed.

    public void setMovieData(List<Movie> movieData) {
    mMovies = movieData;
    notifyDataSetChanged();
    movieClicked = mMovies;


    }*/



}