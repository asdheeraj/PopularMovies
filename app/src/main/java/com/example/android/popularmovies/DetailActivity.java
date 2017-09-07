package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;


public class DetailActivity extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movies movies = getIntent().getParcelableExtra(getString(R.string.ParcelableKey));

        if(savedInstanceState==null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.ParcelableKey),movies);

            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(arguments);

            getFragmentManager().beginTransaction().add(R.id.movie_detail_container,fragment)
                    .commit();

        }




    }
}
