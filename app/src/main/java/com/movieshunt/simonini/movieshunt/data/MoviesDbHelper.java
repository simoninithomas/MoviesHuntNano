package com.movieshunt.simonini.movieshunt.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.movieshunt.simonini.movieshunt.data.MoviesContract.FavoriteEntry;

public class MoviesDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "favorites.db";

    // Version of our database
    private static final int VERSION = 6;

    // Constructor
    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create favorite table
        final String CREATE_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY, " +
                FavoriteEntry.COLUMN_ID + " INTEGER NO NULL," +
                FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_RATING + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_COVER + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_POPULAR + " INTEGER DEFAULT 0,  " +
                FavoriteEntry.COLUMN_TOP_RATED + " INTEGER DEFAULT 0,  " +
                FavoriteEntry.COLUMN_FAVORITE + " INTEGER DEFAULT 0  " + " );";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(db);

    }
}
