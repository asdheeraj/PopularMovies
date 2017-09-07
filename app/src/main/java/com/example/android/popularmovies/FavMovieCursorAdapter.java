package com.example.android.popularmovies;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavMoviesContract;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import static com.example.android.popularmovies.R.id.movieRating;
import static com.example.android.popularmovies.R.id.movieReleaseDate;


public class FavMovieCursorAdapter extends CursorAdapter {

    private ImageView mFavMovie;

    public FavMovieCursorAdapter(Context context, Cursor c) {
        super(context, c,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.movies_grid,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        mFavMovie = (ImageView) view.findViewById(R.id.imageView);

        Picasso.with(context).load(cursor.getString(
                cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL))).fit().into(mFavMovie);


    }
}
