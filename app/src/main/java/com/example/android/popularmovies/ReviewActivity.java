package com.example.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private ListView reviews;
    private ReviewAdapter mAdapter;
    private List<Reviews> mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        reviews = (ListView)findViewById(R.id.reviews);


        View emptyView = findViewById(R.id.emptyReviews);
        reviews.setEmptyView(emptyView);

        mReviews = DetailMovieFragment.reviews;
        mAdapter = new ReviewAdapter(this,mReviews);
        reviews.setAdapter(mAdapter);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


