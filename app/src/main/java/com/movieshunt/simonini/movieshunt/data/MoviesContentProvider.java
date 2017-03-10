package com.movieshunt.simonini.movieshunt.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static android.R.attr.id;
import static com.movieshunt.simonini.movieshunt.data.MoviesContract.FavoriteEntry.TABLE_NAME;

public class MoviesContentProvider extends ContentProvider {
    // Member variable for a MovieDbHelper initialized in the onCreate() method
    private MoviesDbHelper mMoviesDbHelper;

    // By convention one row is 101, 102 etc and one "folder" is a 100, 200...
    public static final int FAVORITES = 100;
    public static final int FAVORITES_WITH_ID = 101;

    // UriMatcher
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    @Override
    public boolean onCreate() {
        // Complete onCreate() and initialize a MoviesDbhelper on startup
        Context context = getContext();
        mMoviesDbHelper = new MoviesDbHelper(context);
        return true;
    }

    public static UriMatcher buildUriMatcher() {
        // New UriMatcher empty object
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        // Directory
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAVORITES, FAVORITES);

        // Single item
        uriMatcher.addURI(MoviesContract.AUTHORITY, MoviesContract.PATH_FAVORITES + "/#", FAVORITES_WITH_ID);

        return uriMatcher;
    }

    /*
        CRUD OPERATIONS
    */
    // INSERT
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // STEP 1: Get access to the task database (to write new data to)
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();

        // STEP 2: Write URI matching code to identify the match for the tasks directory
        final int match = sUriMatcher.match(uri);

        // STEP 3: Insert new values into the database
        // STEP 4: Set the value for the returnedUri and write the default case for unknown URI's
        //returnUri = MovieContract.MovieEntry.buildMovieUri(id);
        Uri returnUri;

        // This method returns a URI, so let's create a var
        switch (match) {
            case FAVORITES:
                // Inserting values into tasks table
                long id = db.insert(TABLE_NAME, null, values);
                // Let's write an if that check this insert was successful
                if (id > 0) {
                    // Success
                    returnUri = ContentUris.withAppendedId(MoviesContract.FavoriteEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert new row into " + uri);
                }
                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        // Step 5: Notify the resolver if the uri has been changed, and return the newly inserted URI
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    // Implement query to handle requests for data by URI
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Step (1) Get access to underlying database (read-only for query)
        final SQLiteDatabase db = mMoviesDbHelper.getReadableDatabase();

        // Step 2 (2) Using our URI matcher to get a match number that identifies the
        // passed in URI
        final int match = sUriMatcher.match(uri);

        // Step 3 Query for the tasks directory and write a default case
        Cursor cursor;
        switch (match) {
            case FAVORITES:
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Default case throws an UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        // Step(4) Set a notification URI on the Cursor and return that Cursor
        // This method returns a URI, so let's create a var
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    // Implement delete to delete a single row of data
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        //1.	Access the db and write URI matching code
        final SQLiteDatabase db = mMoviesDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int favoriteDeleted;

        if (null == selection) {
            selection = "1";
        }
        //2.	Use selection and selection args to delete one row of data
        switch (match) {
            // Handle the single item case, recognized by the ID included in the URI path
            case FAVORITES:

                favoriteDeleted = db.delete(MoviesContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        //3.	Set notification
        if (favoriteDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        //4.	Return the number of deleted items
        return favoriteDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
