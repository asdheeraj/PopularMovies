package com.example.android.popularmovies;

import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.resource;

public class ReviewAdapter extends ArrayAdapter<Reviews> {

    private Context mContext;
    public ReviewAdapter(Context context, List<Reviews> reviews) {
        super(context,0,reviews);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View review = convertView;

        if(review==null) {
            review = LayoutInflater.from(mContext).inflate(R.layout.review_list,parent,false);
        }
        Reviews reviews = getItem(position);

        TextView mReview = (TextView)review.findViewById(R.id.review);
        mReview.setText(reviews.getmReview());

        return review;

    }
}
