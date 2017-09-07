package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.android.popularmovies.data.FavMoviesContract;
import com.example.android.popularmovies.data.FavMoviesDbHelper;

import static android.widget.Toast.makeText;
import static com.example.android.popularmovies.R.drawable.movies;

public class MainActivity extends AppCompatActivity implements MovieFragment.Callback,FavouriteMovieFragment.favouiteCallBack {

    private static final String DETAIL_MOVIE_FRAGMENT_TAG = "DFTAG";
    SharedPreferences sharedPreferences;
    private static String category = "";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(findViewById(R.id.movie_detail_container)!=null) {
            mTwoPane = true;
            if(savedInstanceState == null) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container,new DetailMovieFragment())
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        String listPreference = sharedPreferences.getString
                (getString(R.string.pref_movie_category_key),getString(R.string.pref_movie_category_default_value));

        if(mTwoPane==false ) {

            if(listPreference.equals(getString(R.string.pref_category_favourites_value))) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_movie,new FavouriteMovieFragment())
                        .commit();
            }

            else {
                getFragmentManager().beginTransaction().replace(R.id.fragment_movie,new MovieFragment())
                        .commit();
                }

            } else {

            if((!category.equals(listPreference))) {

                if(listPreference.equals(getString(R.string.pref_category_favourites_value))) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_movie,new FavouriteMovieFragment())
                            .commit();
                }

                else {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_movie,new MovieFragment())
                            .commit();
                }


                category = listPreference;
            }

        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(Movies movies) {
        if(mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.ParcelableKey),movies);

            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container,fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this,DetailActivity.class);
                intent.putExtra(getString(R.string.ParcelableKey),movies);
                startActivity(intent);
        }
    }

    @Override
    public void onMovieSelected(Uri uri) {

        if(mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(getString(R.string.ParcelableKey),uri);

            DetailFavouriteFragment fragment = new DetailFavouriteFragment();
            fragment.setArguments(args);

            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container,fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this,FavMovieActivity.class);
            intent.putExtra(getString(R.string.ParcelableKey),uri);
            startActivity(intent);
        }

    }
}
