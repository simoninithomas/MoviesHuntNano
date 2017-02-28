package com.movieshunt.simonini.movieshunt.models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerList {

    // List of results ("results")
    @SerializedName("results")
    private List<Trailer> results;

    public List<Trailer> getTrailers() {
        return results;
    }

    public void setTrailers(List<Trailer> results) {
        this.results = results;
    }
}
