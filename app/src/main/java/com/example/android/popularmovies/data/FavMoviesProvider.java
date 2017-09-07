package com.example.android.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;


public class FavMoviesProvider extends ContentProvider {

    private FavMoviesDbHelper dbHelper;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int FAV_MOVIES = 123;
    private static final int FAV_MOVIE_ID = 124;

    static {
        sUriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY,FavMoviesContract.PATH_FAV_MOVIES,FAV_MOVIES);
        sUriMatcher.addURI(FavMoviesContract.CONTENT_AUTHORITY,FavMoviesContract.PATH_FAV_MOVIES+"/#",FAV_MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new FavMoviesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor = null;

        switch(match) {
            case FAV_MOVIES:
                cursor = database.query(FavMoviesContract.FavMoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,sortOrder);
                break;
            case FAV_MOVIE_ID:
                selection = FavMoviesContract.FavMoviesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(FavMoviesContract.FavMoviesEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,null,sortOrder);
                break;
            default:
                throw  new IllegalArgumentException("Illegal URI: " +uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAV_MOVIES:
                return FavMoviesContract.FavMoviesEntry.CONTENT_LIST_TYPE;
            case FAV_MOVIE_ID:
                return FavMoviesContract.FavMoviesEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Illegal URI: " + uri + " match with " + match);

        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        int match = sUriMatcher.match(uri);

        switch (match) {
            case FAV_MOVIES:
                return insertFavMovie(uri,contentValues);
            default:
                return null;
        }

    }

    private Uri insertFavMovie(Uri uri,ContentValues values) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        long newRowId = database.insert(FavMoviesContract.FavMoviesEntry.TABLE_NAME,null,values);

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,newRowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {

            case FAV_MOVIES:
                getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(FavMoviesContract.FavMoviesEntry.TABLE_NAME,selection,selectionArgs);

            case FAV_MOVIE_ID:
                selection = FavMoviesContract.FavMoviesEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                getContext().getContentResolver().notifyChange(uri,null);
                return database.delete(FavMoviesContract.FavMoviesEntry.TABLE_NAME,selection,selectionArgs);
        }


        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        return 0;
    }
}
