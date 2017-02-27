package com.example.imran.firebasedemo.Movie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.imran.firebasedemo.AsyncTask.AsyncTaskclass;
import com.example.imran.firebasedemo.Click_Listener_RecyclerView.ItemClickListener;
import com.example.imran.firebasedemo.DataModel.Moviemodel;
import com.example.imran.firebasedemo.Login_Activity;
import com.example.imran.firebasedemo.Movie_Adapter_Recyclerview.MovieAdapter;
import com.example.imran.firebasedemo.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class Movie_list_Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AsyncTaskclass.AsyncResponse {

    private final String URL_TO_HIT = "https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesData.txt";

    AsyncTaskclass jsontask;
    List<Moviemodel> result;
    MovieAdapter movieAdapter;
    Context context;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    Toolbar toolbar;
    String cast;
    Moviemodel moviemodel;
    String pic;
    ImageView imageView;
    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_);

        imageView = (ImageView) findViewById(R.id.imageView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        toolbar.setTitle("TOP HOLLYWOOD MOVIES");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListene(toggle);
       drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Menu menu = navigationView.getMenu();

        initView();
    }

    private void initView() {

        recyclerView = (RecyclerView) findViewById(R.id.rvMovies);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        jsontask = new AsyncTaskclass(context,Movie_list_Activity.this);
        jsontask.asyncResponse=this;
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start


        jsontask.execute(URL_TO_HIT);

        recyclerView.addOnItemTouchListener(
                new ItemClickListener(context, new ItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Display(view, position);
                    }
                })
        );

    }

    @Override
    public void response(List<Moviemodel> result) {
        this.result= result;
        movieAdapter = new MovieAdapter(result,context);
        recyclerView.setAdapter(movieAdapter);


    }

    public void Display(View view, int position) {
        moviemodel = result.get(position);
        StringBuffer buffer = new StringBuffer();
        Intent intent = new Intent(this,MovieDetailsAcivity.class);

        intent.putExtra("image",moviemodel.getImage());
        intent.putExtra("rating",moviemodel.getRating());
        intent.putExtra("movie_name",moviemodel.getMovie());
        intent.putExtra("tag_line",moviemodel.getTagline());
        intent.putExtra("year",moviemodel.getYear());
        intent.putExtra("duration",moviemodel.getDuration());
        intent.putExtra("director",moviemodel.getDirector());
        intent.putExtra("story",moviemodel.getStory());
        for (Moviemodel.Cast cast : moviemodel.getCastList()){
            buffer.append(cast.getName() + ", ");
        }
        cast = buffer.toString();
        intent.putExtra("cast",cast);
        startActivity(intent);
    }


    public void fb_exit() {
            new AlertDialog.Builder(this)
                    .setTitle("Logout?")
                    .setMessage("Are you sure you want to logout from Facebook Account?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            LoginManager.getInstance().logOut();
                            Intent intent = new Intent(Movie_list_Activity.this, Login_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();

    }

        public  void exit(){

            new AlertDialog.Builder(this)
                    .setTitle("Logout?")
                    .setMessage("Are you sure you want to logout?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent intent = new Intent(Movie_list_Activity.this, Login_Activity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }).create().show();
        }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
            fb_exit();
           //super.onBackPressed();
        }
        else {
            exit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.movie_list_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

            if (AccessToken.getCurrentAccessToken() != null && com.facebook.Profile.getCurrentProfile() != null){
                fb_exit();
            }
            else{
                exit();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
