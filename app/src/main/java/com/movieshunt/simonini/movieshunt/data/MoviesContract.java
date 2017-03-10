package com.movieshunt.simonini.movieshunt.data;

import android.content.ContentResolver;
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

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_FAVORITES;

        // Task table and column names
        public static final String TABLE_NAME = "Movies";

        // Since FavoriteEntry implements the interface "BaseColumns", it has an automatically produced
        // "_ID" column in addition to the two below
        public static final String COLUMN_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_COVER = "backdrop_path";
        public static final String COLUMN_SYNOPSIS = "overview";
        public static final String COLUMN_RATING = "vote_average";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_POPULAR = "isPopular";
        public static final String COLUMN_TOP_RATED = "isTopRated";
        public static final String COLUMN_FAVORITE = "isFavorite";

    }
}
