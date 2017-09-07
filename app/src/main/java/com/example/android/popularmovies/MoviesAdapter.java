package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.android.popularmovies.R.id.imageView;


public class MoviesAdapter extends ArrayAdapter<Movies> {

    private Context context;

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public MoviesAdapter(Context context, List<Movies> movies)
    {
        super(context,0,movies);
        this.context = context;
    }



    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View gridView = view;

        if(gridView==null) {
            gridView = LayoutInflater.from(context).inflate(R.layout.movies_grid,viewGroup,false);
        }
        Movies movies = getItem(position);


        ImageView imageView = (ImageView)gridView.findViewById(R.id.imageView);
        Picasso.with(context).load(movies.getmImage()).fit().into(imageView);
        return gridView;
    }
}
