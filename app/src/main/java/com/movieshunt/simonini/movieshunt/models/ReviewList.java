package com.movieshunt.simonini.movieshunt.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReviewList {

    // List of results ("results")
    @SerializedName("results")
    private List<Review> results;

    public List<Review> getReviews() {
        return results;
    }

    public void setReviews(List<Review> results) {
        this.results = results;
    }

}
