package com.example.android.popularmovies;

import android.app.Fragment;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavMoviesContract;

import static com.example.android.popularmovies.R.id.progressBar;


public class FavouriteMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int URI_LOADER = 0;

    String[] projection = {FavMoviesContract.FavMoviesEntry._ID,
            FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_NAME,
            FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL,
            FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_RELEASE_DATE,
            FavMoviesContract.FavMoviesEntry.COLUMN_RATING,
            FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_PLOT};

    private GridView mFavouriteMovies;
    private FavMovieCursorAdapter mAdapter;
    private TextView mEmptyView;
    private ProgressBar progressBar;

    public FavouriteMovieFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie,container,false);
        mFavouriteMovies = (GridView)rootView.findViewById(R.id.movies_gridView);

        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mEmptyView = (TextView) rootView.findViewById(R.id.emptyStateTextView);
        mFavouriteMovies.setEmptyView(mEmptyView);

        getLoaderManager().initLoader(URI_LOADER,null,this);

        mAdapter = new FavMovieCursorAdapter(getActivity(),null);
        mFavouriteMovies.setAdapter(mAdapter);

        mFavouriteMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri favMovieUri = ContentUris.withAppendedId(FavMoviesContract.FavMoviesEntry.CONTENT_URI,id);
                ((favouiteCallBack)getActivity()).onMovieSelected(favMovieUri);

            }
        });

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {

        switch (loaderID) {
            case URI_LOADER:
                return new CursorLoader(getActivity(), FavMoviesContract.FavMoviesEntry.CONTENT_URI,
                        projection,null,null,null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mAdapter.swapCursor(data);
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mAdapter.swapCursor(null);
    }

    public interface favouiteCallBack {

        public void onMovieSelected(Uri uri);
    }
}
