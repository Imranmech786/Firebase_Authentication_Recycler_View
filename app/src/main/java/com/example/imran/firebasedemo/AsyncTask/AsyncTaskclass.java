package com.example.imran.firebasedemo.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.imran.firebasedemo.DataModel.Moviemodel;
import com.example.imran.firebasedemo.Movie.Movie_list_Activity;

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

/**
 * Created by Imran on 2/27/2017.
 */

public class AsyncTaskclass extends AsyncTask<String, Void, List<Moviemodel>> {

    Moviemodel moviemodel;
    Context context;
    List<Moviemodel> result;
    List<Moviemodel> movieModelList;
    public AsyncResponse asyncResponse;
    ProgressDialog dialog;

    public AsyncTaskclass(Context context,Movie_list_Activity movie_list_activity) {

        this.context = context;
        dialog = new ProgressDialog(movie_list_activity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, Please wait...");
        dialog.show();
    }

    @Override
    protected List<Moviemodel> doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        moviemodel = new Moviemodel();

        //movieModelList = new ArrayList<Moviemodel>();

        try {
            URL url  = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line=" ";
            while((line = reader.readLine())!=null){

                buffer.append(line);
            }

            String finalJson = buffer.toString();


            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("movies");
            movieModelList = new ArrayList<Moviemodel>();

            for (int i=0; i<parentArray.length(); i++){
                JSONObject finalObject = parentArray.getJSONObject(i);
                Moviemodel moviemodel = new Moviemodel();
                moviemodel.setMovie(finalObject.getString("movie"));
                moviemodel.setYear("Year: " + finalObject.getString("year"));
                moviemodel.setRating(finalObject.getString("rating"));
                moviemodel.setDirector(finalObject.getString("director"));
                moviemodel.setDuration("Duration: " + finalObject.getString("duration"));
                moviemodel.setTagline(finalObject.getString("tagline"));
                moviemodel.setImage(finalObject.getString("image"));
                moviemodel.setStory(finalObject.getString("story"));

                List<Moviemodel.Cast> castList = new ArrayList<>();
                JSONArray jsonArray = finalObject.getJSONArray("cast");

                for(int j=0;j<jsonArray.length();j++){
                    JSONObject castObject = jsonArray.getJSONObject(j);
                    Moviemodel.Cast cast = new Moviemodel.Cast();
                    cast.setName(castObject.getString("name"));
                    castList.add(cast);
                }
                moviemodel.setCastList(castList);
                movieModelList.add(moviemodel);
            }
            return  movieModelList;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Moviemodel> result) {
        this.result=result;
        dialog.dismiss();

        if (result!= null) {
            super.onPostExecute(result);
            asyncResponse.response(result);

        }
        else{

            Toast.makeText(context, "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
        }
    }

    public interface AsyncResponse
    {
        public void response(List<Moviemodel> result);
    }
}


