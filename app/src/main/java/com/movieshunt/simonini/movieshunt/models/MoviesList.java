package com.movieshunt.simonini.movieshunt.models;

import java.util.List;

public class MoviesList {
    private List<Movies> results;
    private int total_results;

    public List<Movies> getResults() {
        return results;
    }

    public void setResults(List<Movies> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return total_results;
    }

    public void setTotalResults(int total_results) {
        this.total_results = total_results;
    }

}
