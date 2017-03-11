package com.movieshunt.simonini.movieshunt.adapters;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movieshunt.simonini.movieshunt.R;
import com.movieshunt.simonini.movieshunt.models.Movies;
import com.squareup.picasso.Picasso;

import java.util.List;



public class MovieCursorAdapter extends CursorRecyclerViewAdapter<MovieCursorAdapter.ViewHolder> implements

{

    // Specify how many views adapter hold
    private int mNumberItems;

    // Create a final private PosterItemClickListener called mOnClickListener
    final private PosterItemClickListener mOnClickListener;

    private Context context;
    private List<Movies> mMovies;

    // Add an interface called PosterItemClickListener
    // Within that interface, define a void method called onListItemClick that takes an int as a parameter
    public interface PosterItemClickListener {
        void onPosterItemClick(int clickedPosterIndex);
    }

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
    /*
       ADAPTER
    */
    public MovieCursorAdapter(Context context, Cursor cursor, int numberOfItems, PosterItemClickListener listener) {
        super(context,cursor);
        mNumberItems = numberOfItems;
        this.context = context;
        mOnClickListener = listener;
    }



    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.v("onCreateViewHolder", "onCreateViewHolder is called !");
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.poster;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);


        // Set to false, so that the inflated layout will not be
        // immediately attached to its parent viewgroup.
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    //onBindViewHolder()
    @Override
    public void onBindViewHolder(ViewHolder holder, int position, Cursor cursor) {

        Movies movies = Movies.fromCursor(cursor);

        // Get the data model based on position
        Movies movie = mMovies.get(position);

        // Set item views based on your views and data model
        ImageView imageView = holder.posterView;

        Picasso.with(context)
                .load(movies.getPoster())
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        private String mItem;
        // Create a ImageView variable called posterView
        ImageView posterView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         */
        public ViewHolder (View itemView) {
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