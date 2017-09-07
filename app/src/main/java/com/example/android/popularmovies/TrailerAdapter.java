package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.resource;
import static com.example.android.popularmovies.R.id.trailers;

public class TrailerAdapter extends ArrayAdapter<Trailers> {

    private Context context;


    public TrailerAdapter(Context context, List<Trailers> trailer) {
        super(context,0,trailer);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View trailerView = convertView;

        if(trailerView==null) {
            trailerView = LayoutInflater.from(context).inflate(R.layout.trailer_list,parent,false);
        }

        TextView trailerNumber = (TextView)trailerView.findViewById(R.id.trailerNumber);
        String text = context.getString(R.string.trailer) + (position+1);
        trailerNumber.setText(text);

        return trailerView;
    }
}
