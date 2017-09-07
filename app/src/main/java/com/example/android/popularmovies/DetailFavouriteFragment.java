package com.example.android.popularmovies;

import android.app.Fragment;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavMoviesContract;
import com.squareup.picasso.Picasso;


public class DetailFavouriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private static  final int URI_LOADER = 1;
    private Uri favMovieUri;
    private TextView movieReleaseDate;
    private TextView movieRating;
    private ImageView movieThumbnail;
    private TextView movieName;
    private TextView moviePlot;
    private Button deleteFavourite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fav_movie,container,false);

        Bundle arguments = getArguments();
        favMovieUri = arguments.getParcelable(getString(R.string.ParcelableKey));
        movieReleaseDate = (TextView)rootView.findViewById(R.id.movieReleaseDate);
        movieRating = (TextView)rootView.findViewById(R.id.movieRating);
        movieThumbnail = (ImageView)rootView.findViewById(R.id.movieDetail);
        movieName = (TextView)rootView.findViewById(R.id.movieName);
        moviePlot = (TextView)rootView.findViewById(R.id.moviePlot);
        deleteFavourite = (Button)rootView.findViewById(R.id.deleteFavourite);

        deleteFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favMovieUri!=null) {
                    getActivity().getContentResolver().delete(favMovieUri,null,null);
                    Toast.makeText(getActivity(), R.string.deleteFavourite,Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(URI_LOADER,null,this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {FavMoviesContract.FavMoviesEntry._ID,
                FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_NAME,
                FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL,
                FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
                FavMoviesContract.FavMoviesEntry.COLUMN_RATING,
                FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_PLOT};

        return new CursorLoader(getActivity(),favMovieUri,projection,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        if (cursor.moveToFirst()) {

            movieReleaseDate.setText(cursor.getString(
                    cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_RELEASE_DATE)));
            movieRating.setText(cursor.getString(
                    cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_RATING))+getString(R.string.rating_Scale));
            movieName.setText(cursor.getString(
                    cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_NAME)));
            moviePlot.setText(cursor.getString(
                    cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_PLOT)));
            Picasso.with(getActivity()).load(cursor.getString(
                    cursor.getColumnIndex(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL))).fit().into(movieThumbnail);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
