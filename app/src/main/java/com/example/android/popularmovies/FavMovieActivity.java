package com.example.android.popularmovies;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.data.FavMoviesContract;
import com.squareup.picasso.Picasso;

public class FavMovieActivity extends AppCompatActivity  {

    private Uri favMovieUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_movie);

        favMovieUri = getIntent().getParcelableExtra(getString(R.string.ParcelableKey));

        if(savedInstanceState==null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.ParcelableKey),favMovieUri);

            DetailFavouriteFragment fragment = new DetailFavouriteFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction().add(R.id.favourite_detail_container,fragment)
                    .commit();

        }

    }
}
