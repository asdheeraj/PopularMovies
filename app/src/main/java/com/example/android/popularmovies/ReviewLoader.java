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

import static com.example.android.popularmovies.R.id.trailers;


public class ReviewLoader extends AsyncTaskLoader<List> {

    private String mURL;
    public ReviewLoader(Context context,String Url) {
        super(context);
        mURL = Url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Reviews> loadInBackground() {
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

        return getReviewList(jsonResponse);
    }

    private List<Reviews> getReviewList(String jsonResponse) {

        ArrayList<Reviews> reviews = new ArrayList<>();

        if(jsonResponse!=null) {

            try {
                JSONObject jsonMovies = new JSONObject(jsonResponse);
                JSONArray trailerResults = jsonMovies.getJSONArray(getContext().getString(R.string.jsonArray_Results));

                for(int i=0;i<trailerResults.length();i++) {
                    JSONObject index = trailerResults.getJSONObject(i);
                    String review = index.getString(getContext().getString(R.string.reviewKey));
                    reviews.add(new Reviews(review));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return reviews;
    }
}
