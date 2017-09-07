package com.example.android.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.popularmovies.data.FavMoviesContract.FavMoviesEntry;


public class FavMoviesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "favourites.db";

    public  static final String SQL_CREATE_FAVOURITES_TABLE =
            "CREATE TABLE " + FavMoviesEntry.TABLE_NAME + " ( " + FavMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavMoviesEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " + FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL + " TEXT NOT NULL, "
            + FavMoviesEntry.COLUMN_MOVIE_PLOT + " TEXT NOT NULL, " + FavMoviesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, "
            + FavMoviesEntry.COLUMN_RATING + " TEXT NOT NULL);";

    public FavMoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(SQL_CREATE_FAVOURITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
