package com.movieshunt.simonini.movieshunt.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.movieshunt.simonini.movieshunt.R;
import com.movieshunt.simonini.movieshunt.models.Review;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviews;
    private int rowLayout;
    private Context context;

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        LinearLayout reviewsLayout;
        TextView review;
        TextView author;

        public ReviewViewHolder(View v) {
            super(v);
            reviewsLayout = (LinearLayout) v.findViewById(R.id.ll_review);
            review = (TextView) v.findViewById(R.id.tv_review);
            author = (TextView) v.findViewById(R.id.tv_author);
        }
    }

    public ReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review;

        // Inflate our new item view using a LayoutInflater. It takes the ID of layout in xml.
        // Then --> inflates or converts this collection of view groups and views.
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, final int position) {

        // Get the data model based on position
        Review review = reviews.get(position);
        holder.review.setText(reviews.get(position).getContent());
        holder.author.setText(reviews.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
