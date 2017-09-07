package com.example.android.popularmovies.data;


import android.content.ContentResolver;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.BaseColumns;

import com.example.android.popularmovies.R;

public class FavMoviesContract {
    
    private FavMoviesContract() {
        
    }



    public static final String CONTENT_AUTHORITY = "com.example.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_FAV_MOVIES = "FavMovies";

    public static  final class FavMoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_FAV_MOVIES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV_MOVIES ;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV_MOVIES ;

        public static final String TABLE_NAME ="FavMovies";
        public static final String _ID = BaseColumns._ID;
        public static final String  COLUMN_MOVIE_NAME = "Name";
        public static final String COLUMN_MOVIE_IMAGE_URL = "ImageURL";
        public static final String COLUMN_MOVIE_PLOT = "Plot";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "ReleaseDate";
        public static final String COLUMN_RATING = "Rating";

    }

    
    
}
