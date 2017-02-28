package com.movieshunt.simonini.movieshunt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.movieshunt.simonini.movieshunt.R;
import com.movieshunt.simonini.movieshunt.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private final TrailerItemClickListener mOnClickListener;
    private Context context;
    private List<Trailer> mTrailers;

    /*
        RESPONDING TO CLICK ITEMS
*/
    // Step 1: Create an interface that will define our listener
    public interface TrailerItemClickListener {
        // Method that takes an int as parameter
        void onClick(int clickedItemIndex);

    }

    /*
        VIEW HOLDER
     */
    // ViewHolders cache the references to the views that will be modified in the adapter.
    // We create a class called MovieViewHolder that extends Recycler.ViewHolder
    //
    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // Create a ImageView variable called posterView
        LinearLayout trailersLayout;
        TextView trailerTitle;
        ImageView trailerImage;

        // Create a constructor for MovieViewHolder that accepts a View called itemView as a parameter

        public TrailerViewHolder(View itemView) {
            super(itemView);


            trailersLayout = (LinearLayout) itemView.findViewById(R.id.ll_trailer);
            trailerImage = (ImageView) itemView.findViewById(R.id.iv_trailer);
            trailerTitle = (TextView) itemView.findViewById(R.id.tv_video_title);

            // Call setOnClickListener on the View passed into the constructor
            // (use 'this' as the OnClickListener)
            itemView.setOnClickListener(this);
        }

        /* Override onClickMethod*/
        @Override
        public void onClick(View view) {
            // Get the adapter position (item that was selected)
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onClick(clickedPosition);
        }
    }

    /*
        ADAPTER
     */
    // Specify how many views adapter hold
    private int mNumberItems;

    // Store a member variable for the titles


    // Populate that var in the constructor
    public TrailerAdapter(List<Trailer> trailers, Context context, TrailerItemClickListener listener) {
        //mNumberItems = numberOfItems;
        mTrailers = trailers;
        mOnClickListener = listener;
        this.context = context;

    }

    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.v("onCreateViewHolder", "onCreateViewHolder is called !");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);

        return viewHolder;

    }


    //onBindViewHolder()
    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {

        Log.v("We inflate ?", "Seems yes");

        // Get the data model based on position

        Trailer trailer = mTrailers.get(position);

        // Set item views based on your views and data model
        //TextView textView = holder.listItemNumberView;
        //textView.setText(movie.getMovieTitle());


        // Set item views based on your views and data model
        ImageView imageView = holder.trailerImage;

        Picasso.with(context)
                .load(trailer.getTrailerImage())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(imageView);

        holder.trailerTitle.setText(trailer.getVideoTitle());

    }

    //getItemCount() : returns the mNumberItems var
    @Override
    public int getItemCount() {
        mNumberItems = mTrailers.size();
        return mNumberItems;
    }

    //


    /**
     * This method is used to set the movie data on a MovieAdapter if we've already
     * created one. This is handy when we get new data from the web but don't want to create a
     * new ForecastAdapter to display it.
     *
     * @param trailerData The new moviesData data to be displayed.
     */
    public void setTrailerData(List<Trailer> trailerData) {
        mTrailers = trailerData;
        notifyDataSetChanged();
        //trailerClicked = mTrailers;


    }

}