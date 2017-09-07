package com.example.android.popularmovies;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.FavMoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.id.list;
import static com.example.android.popularmovies.R.id.movieName;
import static com.example.android.popularmovies.R.id.moviePlot;
import static com.example.android.popularmovies.R.id.movieRating;
import static com.example.android.popularmovies.R.id.movieReleaseDate;

public class DetailMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<List> {


    private static final String MOVIES_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    private static final int TRAILER_LOADER = 100;
    private static final String TRAILERS = "videos";
    private static final String REVIEWS = "reviews";
    private static final String TRAILER_LINK = "https://www.youtube.com/watch?v=";
    private static final int REVIEW_LOADER =  101;



    //Binding the views of the layout with Java code using Butterknife



    private String mImageURL;
    private String mName;
    private String mDate;
    private String mRating;
    private String mPlot;
    private int mId;
    private  TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    public static List<Trailers> trailers;
    public static List<Reviews> reviews;
    private Button addFavourite;
    private Movies movies;
    private TextView viewTrailers;
    private TextView viewReviews;
    private View rootView;

public DetailMovieFragment() {

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ButterKnife.bind(getActivity());

        trailers = new ArrayList<>();
        reviews = new ArrayList<>();
        Bundle arguments = getArguments();
        if(arguments!=null) {
             rootView = inflater.inflate(R.layout.fragment_movie_detail,container,false);
            ImageView movieThumbnail = (ImageView)rootView.findViewById(R.id.movieDetail);
            TextView movieReleaseDate = (TextView)rootView.findViewById(R.id.movieReleaseDate);
            TextView movieRating = (TextView)rootView.findViewById(R.id.movieRating);
            TextView movieName = (TextView)rootView.findViewById(R.id.movieName);
            TextView moviePlot = (TextView)rootView.findViewById(R.id.moviePlot);
            addFavourite = (Button)rootView.findViewById(R.id.addFavourite);
             viewTrailers = (TextView)rootView.findViewById(R.id.viewTrailers);
           viewReviews = (TextView)rootView.findViewById(R.id.viewReviews);
             movies = arguments.getParcelable(getString(R.string.ParcelableKey));
            mImageURL = movies.getmImage();
            mName = movies.getmTitle();
            mDate = movies.getmReleaseDate();
            mRating = movies.getmRating();
            mPlot = movies.getmPlot();
            mId = movies.getmId();
            Picasso.with(getActivity()).load(mImageURL).fit().into(movieThumbnail);

            movieReleaseDate.setText(mDate);
            movieRating.setText(mRating+getString(R.string.rating_Scale));
            movieName.setText(mName);
            moviePlot.setText(mPlot);


            addFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_NAME,mName);
                    contentValues.put(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_IMAGE_URL,mImageURL);
                    contentValues.put(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_RELEASE_DATE,mDate);
                    contentValues.put(FavMoviesContract.FavMoviesEntry.COLUMN_RATING,mRating);
                    contentValues.put(FavMoviesContract.FavMoviesEntry.COLUMN_MOVIE_PLOT,mPlot);

                    getActivity().getContentResolver().insert(FavMoviesContract.FavMoviesEntry.CONTENT_URI,contentValues);
                }
            });


            viewTrailers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),TrailerActivity.class);
                    startActivity(intent);
                }
            });



            viewReviews.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),ReviewActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            rootView = inflater.inflate(R.layout.empty_detail_fragment,container,false);
        }


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TRAILER_LOADER,null,this);
        getLoaderManager().initLoader(REVIEW_LOADER,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<List> onCreateLoader(int loaderID, Bundle args) {
        String api_key = getString(R.string.api_key);
        Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);
        Uri.Builder builder  = baseUri.buildUpon();
        builder.appendEncodedPath(String.valueOf(mId));
        builder.appendQueryParameter("api_key",api_key);
        String url;

        switch(loaderID) {
            case TRAILER_LOADER:
                builder.appendEncodedPath(TRAILERS);
                url  = builder.toString();
                return  new TrailerLoader(getActivity(),url);

            case REVIEW_LOADER:
                builder.appendEncodedPath(REVIEWS);
                url  = builder.toString();
                return new ReviewLoader(getActivity(),url);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<List> loader, List list) {

        if(list.size()!=0) {
            if(list.get(0) instanceof Trailers) {
                trailers = list;
            } else if(list.get(0) instanceof Reviews) {
                reviews = list;
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<List> loader) {

        trailers.clear();
        reviews.clear();

    }
}
