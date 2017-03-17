package com.movieshunt.simonini.movieshunt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movieshunt.simonini.movieshunt.R;
import com.movieshunt.simonini.movieshunt.models.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    List<Trailer> mTrailers;
    private Context context;
    // Specify how many views adapter hold
    private int mNumberItems;
    // Create a final private PosterItemClickListener called mOnClickListener
    final private TrailerItemClickListener mOnClickListener;

    // Add an interface called PosterItemClickListener
    // Within that interface, define a void method called onListItemClick that takes an int as a parameter
    public interface TrailerItemClickListener {
        void onTrailerItemClick(int clickedTrailerIndex);
    }

    // Add a ListItemClickListener as a parameter to the constructor and store it in mOnClickListener
    /*
       ADAPTER
    */

    public TrailerAdapter(Context context, int numberOfItems, List<Trailer> trailers, TrailerItemClickListener listener) {
        mNumberItems = numberOfItems;
        mTrailers = trailers;
        this.context = context;
        mOnClickListener = listener;
    }

    // Override our 3 functions
    // onCreateViewHolder()
    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
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

        // Get the data model based on position
        Trailer trailer = mTrailers.get(position);

        // Set item views based on your views and data model
        ImageView trailerImage = holder.trailerImage;

        Picasso.with(context)
                .load(trailer.getTrailerImage())
                .placeholder(R.drawable.load)
                .error(R.drawable.ic_picture_error)
                .into(trailerImage);

        holder.trailerTitle.setText(trailer.getVideoTitle());

    }

    //getItemCount() : returns the mNumberItems var
    @Override
    public int getItemCount() {
        mNumberItems = mTrailers.size();
        return mNumberItems;
    }

      /*
        VIEW HOLDER
       */

    /**
     * Cache of the children views for a list item.
     */
    class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private String mItem;
        LinearLayout trailersLayout;
        TextView trailerTitle;
        ImageView trailerImage;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
         */
        public TrailerViewHolder(View itemView) {
            super(itemView);

            // Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            trailersLayout = (LinearLayout) itemView.findViewById(R.id.ll_trailer);
            trailerImage = (ImageView) itemView.findViewById(R.id.iv_trailer);
            trailerTitle = (TextView) itemView.findViewById(R.id.tv_video_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onTrailerItemClick(clickedPosition);
        }
    }
}