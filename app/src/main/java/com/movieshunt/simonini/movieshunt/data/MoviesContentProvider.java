package com.movieshunt.simonini.movieshunt.data;


import android.content.ContentProvider;
import android.content.Context;

public class MoviesContentProvider extends ContentProvider{
    // Member variable for a MovieDbHelper initialized in the onCreate() method
    private MoviesDbHelper mMoviesDbHelper;

    @Override
    public boolean onCreate() {
        // Complete onCreate() and initialize a MoviesDbhelper on startup
        Context context = getContext();
        mMoviesDbHelper = new MoviesDbHelper(context);
        return false;
    }

}
