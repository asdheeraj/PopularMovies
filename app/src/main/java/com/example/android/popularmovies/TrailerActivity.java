package com.example.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TrailerActivity extends AppCompatActivity {

    private ListView trailers;
    private List<Trailers> mTrailers;
    private static final String TRAILER_LINK = "https://www.youtube.com/watch?v=";
    private TrailerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        trailers = (ListView)findViewById(R.id.trailers);
        View emptyView = findViewById(R.id.emptyTrailers);
        trailers.setEmptyView(emptyView);
        mTrailers = new ArrayList<>();
        mTrailers = DetailMovieFragment.trailers;
        mAdapter = new TrailerAdapter(this,mTrailers);
        trailers.setAdapter(mAdapter);
        trailers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Trailers trailerKey = mAdapter.getItem(position);
                String trailerLink = TRAILER_LINK+trailerKey.getmKey();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink));
                startActivity(intent);
            }
        });
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
