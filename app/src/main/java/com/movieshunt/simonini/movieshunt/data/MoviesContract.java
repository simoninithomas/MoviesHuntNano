package com.movieshunt.simonini.movieshunt.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Keep tracts on any constants that access our data.
 */

public class MoviesContract {
    // Add content provider constants to the Contract

    // The authority, which is how your code know which Content Provider to access
    public static final String AUTHORITY = "com.movieshunt.simonini.movieshunt";

    // Base content URI "content://" + AUTHORITY
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    public static final String PATH_FAVORITES = "favorites";

    // FavoriteEntry is an inner class that defines the contents of the task table
    public static final class FavoriteEntry implements BaseColumns {
        // FavoriteEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();



        // Task table and column names
        public static final String TABLE_NAME = "favorites";

        // Since TaskEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_DATE = "Date";
        public static final String COLUMN_RATING = "Rating";
        public static final String COLUMN_SYNOPSIS = "Synopsis";
        public static final String COLUMN_COVER = "CoverUrl";
        public static final String COLUMN_POSTER = "PosterUrl";
        public static final String COLUMN_TRAILER = "Trailer";
        public static final String COLUMN_REVIEWS = "Reviews";
        public static final String COLUMN_FAVORITE = "Favorite?";



    }
}
