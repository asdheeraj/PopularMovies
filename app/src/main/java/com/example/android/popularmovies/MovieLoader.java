package com.example.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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


public class MovieLoader extends AsyncTaskLoader<List<Movies>> {

    private  String mURL;

    public MovieLoader(Context context,String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movies> loadInBackground() {
        String jsonResponse = null;
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        ConnectivityManager connectivityManager =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo  = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null) {

            try {
                //Create the request to theMovieDB api to get the response
                URL url = new URL(mURL);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod(getContext().getString(R.string.httpURL_setRequestMethod));
                httpURLConnection.connect();

                //Read the input stream to a string

                InputStream inputStream = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream==null)
                {
                    //Do nothing, return Empty JSON String
                    jsonResponse = null;
                }

                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line=bufferedReader.readLine())!=null)
                {
                    buffer.append(line+"\n");
                }

                if(buffer.length()==0)
                {
                    jsonResponse = null;
                }

                //Convert the buffer into a String
                jsonResponse = buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                jsonResponse = null;
            }finally {
                if(httpURLConnection!=null) {
                    httpURLConnection.disconnect();
                }
                if(bufferedReader!=null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else {
            jsonResponse = null;
        }

        return getPopularMovieList(jsonResponse);
    }

    private List<Movies> getPopularMovieList(String jsonResponse) {

        ArrayList<Movies> popularMovies = new ArrayList<>();

        if(jsonResponse!=null) {

            try {
                JSONObject jsonMovies = new JSONObject(jsonResponse);
                JSONArray movieResults = jsonMovies.getJSONArray(getContext().getString(R.string.jsonArray_Results));

                for(int i=0;i<movieResults.length();i++) {
                    JSONObject index = movieResults.getJSONObject(i);
                    String imagePath = index.getString(getContext().getString(R.string.json_ImagePath));
                    String moviePlot = index.getString(getContext().getString(R.string.json_movieOverview));
                    String releaseDate = index.getString(getContext().getString(R.string.json_movieReleaseDate));
                    String title = index.getString(getContext().getString(R.string.json_movieTitle));
                    String rating = index.getString(getContext().getString(R.string.json_movieRating));
                    int id = index.getInt(getContext().getString(R.string.Movie_ID));
                    popularMovies.add(new Movies(imagePath,moviePlot,releaseDate,title,rating,id));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return popularMovies;
    }
}
