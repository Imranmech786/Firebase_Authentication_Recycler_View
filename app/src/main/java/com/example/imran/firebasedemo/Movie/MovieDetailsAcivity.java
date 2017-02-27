package com.example.imran.firebasedemo.Movie;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imran.firebasedemo.DataModel.Moviemodel;
import com.example.imran.firebasedemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MovieDetailsAcivity extends AppCompatActivity {

    ImageView image_moviedetail;
    RatingBar ratingBar_moviedetail;
    TextView moviename_moviedetail;
    TextView tagline_moviedetail;
    TextView year_moviedetail;
    TextView duration_moviedetail;
    TextView director_moviedetail;
    TextView cast_moviedetail;
    TextView story_moviedetail;
    Moviemodel moviemodel;
    //ProgressBar progressBar;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_acivity);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        //toolbar.setTitle(getIntent().getStringExtra("movie_name"));
        collapsingToolbarLayout =(CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        //setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        initView();

        display_moviedetail();
    }

    public void initView(){

        moviemodel = new Moviemodel();
        image_moviedetail = (ImageView) findViewById(R.id.image_moviedetail);
        ratingBar_moviedetail = (RatingBar) findViewById(R.id.ratingbar_moviedetail);
        moviename_moviedetail = (TextView) findViewById(R.id.moviename_moviedetail);
        tagline_moviedetail = (TextView) findViewById(R.id.tagline_moviedetail);
        year_moviedetail = (TextView) findViewById(R.id.year_moviedetail);
        duration_moviedetail = (TextView) findViewById(R.id.duration_moviedetail);
        director_moviedetail = (TextView) findViewById(R.id.director_moviedetail);
        cast_moviedetail = (TextView) findViewById(R.id.cast_moviedetail);
        story_moviedetail = (TextView) findViewById(R.id.story_moviedetail);
        //progressBar = (ProgressBar) findViewById(R.id.progressbar_moviedetail);
    }

    private void display_moviedetail() {

        Intent intent = getIntent();

        ratingBar_moviedetail.setRating(Float.parseFloat(getIntent().getStringExtra("rating"))/2);
        ratingBar_moviedetail.setIsIndicator(true);
        moviename_moviedetail.setText(getIntent().getStringExtra("movie_name"));

        //toolbar.setTitle(getIntent().getStringExtra("movie_name"));
        //collapsingToolbarLayout.setTitle(getIntent().getStringExtra("movie_name") + "Details");
        collapsingToolbarLayout.setTitle(" ");

        tagline_moviedetail.setText(getIntent().getStringExtra("tag_line"));
        year_moviedetail.setText(getIntent().getStringExtra("year"));
        duration_moviedetail.setText(getIntent().getStringExtra("duration"));
        director_moviedetail.setText(getIntent().getStringExtra("director"));
        cast_moviedetail.setText(getIntent().getStringExtra("cast"));
        story_moviedetail.setText( getIntent().getStringExtra("story"));

        ImageLoader.getInstance().displayImage(getIntent().getStringExtra("image"), image_moviedetail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                //getApplicationContext().onBackPressed();
                //   return true;
                //default:
                finish();

        }
        return super.onOptionsItemSelected(item);
    }




}
