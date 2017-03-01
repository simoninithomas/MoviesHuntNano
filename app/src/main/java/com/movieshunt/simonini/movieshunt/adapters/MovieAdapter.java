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

    // Specify how many views adapter hold
    private int mNumberItems;
    // Create a final private PosterItemClickListener called mOnClickListener
    final private PosterItemClickListener mOnClickListener;

    // Add an interface called PosterItemClickListener
    // Within that interface, define a void method called onListItemClick that takes an int as a parameter

    public interface PosterItemClickListener {
        void onPosterItemClick(int clickedPosterIndex);
    }

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener

    /*
       ADAPTER
    */

    public MovieAdapter(Context context, int numberOfItems, List<Movies> movies, PosterItemClickListener listener) {
        mNumberItems = numberOfItems;
        mMovies = movies;
        this.context = context;
        mOnClickListener = listener;

    }





    private Context context;
    private List<Movies> mMovies;


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

      /*
   VIEW HOLDER
*/

    /**
     * Cache of the children views for a list item.
     */
    class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

            private String mItem;
            // Create a ImageView variable called posterView
            ImageView posterView;


        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         *
         */
        public MovieViewHolder(View itemView) {
            super(itemView);

         // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)

            posterView = (ImageView) itemView.findViewById(R.id.tv_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onPosterItemClick(clickedPosition);
        }
    }
}