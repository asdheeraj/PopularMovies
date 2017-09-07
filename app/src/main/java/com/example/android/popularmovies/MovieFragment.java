package com.example.android.popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.LoaderManager;
import android.content.Loader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
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

import static android.view.View.GONE;


public class MovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movies>> {


    private GridView mMoviesGridView;
    public static final int ASYNCTASK_LOADER = 0;
    private MoviesAdapter moviesAdapter;
    private int mPosition = GridView.INVALID_POSITION;
    private static final String MOVIE_SELECTED = "selected_movie" ;
    SharedPreferences sharedPreferences;
    private List<Movies> popularMovies;
    private static String category = "";
    private TextView mEmptyView;
    private static final String MOVIES_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    private ProgressBar progressBar;

    public MovieFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie,container,false);
        mMoviesGridView = (GridView)rootView.findViewById(R.id.movies_gridView);
        mEmptyView = (TextView) rootView.findViewById(R.id.emptyStateTextView);
        mMoviesGridView.setEmptyView(mEmptyView);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        popularMovies = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        mMoviesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movies movies = moviesAdapter.getItem(position);
                ((Callback)getActivity()).onItemSelected(movies);
                mPosition = position;
            }
        });




        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String listPreference = sharedPreferences.getString(getString(R.string.pref_movie_category_key),getString(R.string.pref_movie_category_default_value));
        if(((!category.equals(listPreference))||popularMovies.isEmpty())&& !listPreference.equals(getString(R.string.pref_category_favourites_value))) {
            Bundle args = new Bundle();
            args.putString("preference",listPreference);
            getLoaderManager().initLoader(ASYNCTASK_LOADER,args,this);
            category = listPreference;
        }
        else {
            //do nothing
        }

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_SELECTED)) {
            mPosition = savedInstanceState.getInt(MOVIE_SELECTED);
            Log.v("Check","Position is: "+mPosition);
            if (mPosition != GridView.INVALID_POSITION) {
                mMoviesGridView.setSelection(mPosition);
            }

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Gridview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(MOVIE_SELECTED, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public Loader<List<Movies>> onCreateLoader(int loaderID, Bundle args) {

        switch (loaderID) {
            case ASYNCTASK_LOADER:
                String api_key = getString(R.string.api_key);
                //Create the Base URI
                Uri baseUri = Uri.parse(MOVIES_REQUEST_URL);
                Uri.Builder builder  = baseUri.buildUpon();
                builder.appendEncodedPath(args.getString("preference"));
                builder.appendQueryParameter("api_key",api_key);
                    String url  = builder.toString();
                    return new MovieLoader(getActivity(),url);
            default:
                return null;
        }
    }


    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> movies) {

        if(movies.size()!=0) {
            moviesAdapter = new MoviesAdapter(getActivity(),movies);
            mMoviesGridView.setAdapter(moviesAdapter);

        }
        else {
            Toast toast =  Toast.makeText(getActivity(), R.string.network_Connection_Message,Toast.LENGTH_SHORT);
            TextView textView = (TextView)toast.getView().findViewById(android.R.id.message);
            if(textView!=null) {
                textView.setGravity(Gravity.CENTER);
            }
            toast.show();
        }

        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {
        moviesAdapter.clear();
    }


    public interface Callback {

        public void onItemSelected(Movies movies);
    }

}
