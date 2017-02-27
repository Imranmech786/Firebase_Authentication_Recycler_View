package com.example.imran.firebasedemo.Movie_Adapter_Recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.imran.firebasedemo.DataModel.Moviemodel;
import com.example.imran.firebasedemo.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by Imran on 2/27/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    List<Moviemodel> moviemodelList;
    Moviemodel moviemodel;
    Context context;


    public MovieAdapter(List<Moviemodel> moviemodelList,Context context) {



        this.moviemodelList = moviemodelList;
        this.context=context;
        //this.resource=resource;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivMovieIcon;
        private TextView tvMovie;
        private TextView tvTagline;
        private TextView tvYear;
        private TextView tvDuration;
        private TextView tvDirector;
        private RatingBar rbMovieRating;
        private TextView tvCast;
        private TextView tvStory;
        private ProgressBar progressBar;

        public ViewHolder(final View itemView) {

            super(itemView);

            ivMovieIcon = (ImageView)itemView.findViewById(R.id.movie_poster);
            tvMovie = (TextView) itemView.findViewById(R.id.movie_title);
            tvTagline = (TextView)itemView.findViewById(R.id.movie_tagline);
            tvYear = (TextView)itemView.findViewById(R.id.movie_year);
            tvDuration = (TextView)itemView.findViewById(R.id.movie_duration);
            tvDirector = (TextView)itemView.findViewById(R.id.movie_director);
            rbMovieRating = (RatingBar) itemView.findViewById(R.id.movie_rating);
            tvStory = (TextView)itemView.findViewById(R.id.movie_story);
            tvCast = (TextView)itemView.findViewById(R.id.movie_cast);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressbar);
            rbMovieRating.setIsIndicator(true); //to make the Rating bar fixed
            // ratingBar.setFocusable(false);
        }


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        moviemodel= moviemodelList.get(position);

        holder.tvMovie.setText(moviemodel.getMovie());
        holder.tvTagline.setText(moviemodel.getTagline());
        holder.tvYear.setText(moviemodel.getYear());
        holder.tvDuration.setText(moviemodel.getDuration());
        holder.tvDirector.setText(moviemodel.getDirector());
        holder.rbMovieRating.setRating(Float.parseFloat(moviemodel.getRating())/2);
        holder.tvStory.setText("Story: "+ moviemodel.getStory());
        StringBuffer buffer = new StringBuffer();
        for (Moviemodel.Cast cast : moviemodel.getCastList()) {
            buffer.append(cast.getName() + ", ");
        }
        holder.tvCast.setText("Cast:" + buffer);
        ImageLoader.getInstance().displayImage(moviemodel.getImage(), holder.ivMovieIcon, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.progressBar.setVisibility(View.GONE);
            }
        });

        holder.rbMovieRating.setVisibility(View.GONE);
        holder.tvCast.setVisibility(View.GONE);
        holder.tvStory.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return moviemodelList.size();
    }

}
