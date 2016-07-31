package com.trivago.traktv.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.trivago.traktv.R;
import com.trivago.traktv.bean.MovieBean;
import com.trivago.traktv.listener.IMovieListLoadedListener;
import com.trivago.traktv.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class LoadMovieListTask extends AsyncTask<String,String,String> {


    private IMovieListLoadedListener iMoviesLoadedListener;
    private Context mContext;
    private String mUrl;
    private ProgressDialog progress;

    public LoadMovieListTask(Context mContext, String mUrl, IMovieListLoadedListener iMoviesLoadedListener) {
        this.mContext = mContext;
        this.mUrl = mUrl;
        this.iMoviesLoadedListener = iMoviesLoadedListener;

    }

    @Override
    protected String doInBackground(String... params) {
        try {

            if (this.isCancelled())
                return mContext.getResources().getString(R.string.cancelled);

            URL obj = new URL(this.mUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add request header
            /**
             Content-Type:application/json
             trakt-api-version:2
             trakt-api-key:[client_id]
             * */
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("trakt-api-version", "2");
            con.setRequestProperty("trakt-api-key", Constants.CLIENT_ID);

            int responseCode = con.getResponseCode();

            if (this.isCancelled())
                return "cancled";

            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                if (this.isCancelled())
                    return mContext.getResources().getString(R.string.cancelled);
                return response.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }// catch (ProtocolException e) {
        catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPreExecute(){
        //progress = ProgressDialog.show(mContext, "",
          //      "Loading");

    }

    @Override
    protected void onPostExecute(String s) {
//        progress.dismiss();
        if (s == null)
            return;

        if (s.equals(mContext.getResources().getString(R.string.cancelled)) || this.isCancelled())
            return;

        ArrayList<MovieBean> movieList = new ArrayList<>();
        if(mUrl.contains("?query=")){
            movieList = parseSearchQueryResponse(s);
        }else {
            movieList = parseResponse(s);
        }
        if (s.equals(mContext.getResources().getString(R.string.cancelled)) || this.isCancelled())
            return;
        iMoviesLoadedListener.onMoviesLoaded(movieList);

    }

    private ArrayList<MovieBean> parseSearchQueryResponse(String s) {

        ArrayList<MovieBean> movieList = new ArrayList<>();
        Resources resources = mContext.getResources();
        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("movie");
                MovieBean movie = new MovieBean();

                if (!jsonObject.isNull(resources.getString(R.string.title)))
                    movie.setTitle(jsonObject.getString(resources.getString(R.string.title)));
                if (!jsonObject.isNull(resources.getString(R.string.year)))
                    movie.setmYear(jsonObject.getInt(resources.getString(R.string.year)));
                if (!jsonObject.isNull(resources.getString(R.string.overview)))
                    movie.setmOverView(jsonObject.getString(resources.getString(R.string.overview)));
                if (!jsonObject.isNull(resources.getString(R.string.images))){
                    JSONObject jj = jsonObject.getJSONObject(resources.getString(R.string.images)).getJSONObject(resources.getString(R.string.poster));
                    if (!jj.isNull(resources.getString(R.string.thumb))){
                        movie.setmBanner(jj.getString(resources.getString(R.string.thumb)));
                    }
                }

                if (!jsonObject.isNull(resources.getString(R.string.ids))){
                    JSONObject joID = jsonObject.getJSONObject(resources.getString(R.string.ids));
                    if (!joID.isNull(resources.getString(R.string.imdb)))
                        movie.setmIdIMDB(joID.getString(resources.getString(R.string.imdb)));
                    if (!joID.isNull(resources.getString(R.string.tmdb)))
                        movie.setmIdTMDB(joID.getString(resources.getString(R.string.tmdb)));
                    if (!joID.isNull(resources.getString(R.string.trakt)))
                        movie.setmTrakt(joID.getInt(resources.getString(R.string.trakt)));
                    if (!joID.isNull(resources.getString(R.string.slug)))
                        movie.setmSlug(joID.getString(resources.getString(R.string.slug)));
                }

                movieList.add(movie);
            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<MovieBean> parseResponse(String s) {
        ArrayList<MovieBean> movieList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Resources resources = mContext.getResources();
                MovieBean movie = new MovieBean();
                if (!jsonObject.isNull(resources.getString(R.string.title)))
                    movie.setTitle(jsonObject.getString(resources.getString(R.string.title)));
                if (!jsonObject.isNull(resources.getString(R.string.year)))
                    movie.setmYear(jsonObject.getInt(resources.getString(R.string.year)));
                if (!jsonObject.isNull(resources.getString(R.string.tagline)))
                    movie.setmTagLine(jsonObject.getString(resources.getString(R.string.tagline)));
                if (!jsonObject.isNull(resources.getString(R.string.overview)))
                    movie.setmOverView(jsonObject.getString(resources.getString(R.string.overview)));
                if (!jsonObject.isNull(resources.getString(R.string.runtime)))
                    movie.setmRunTime(jsonObject.getInt(resources.getString(R.string.runtime)));
                if (!jsonObject.isNull(resources.getString(R.string.trailer)));
                    movie.setmTrailer(jsonObject.getString(resources.getString(R.string.trailer)));
                if (!jsonObject.isNull(resources.getString(R.string.homepage)))
                    movie.setmHomePage(jsonObject.getString(resources.getString(R.string.homepage)));
                if (!jsonObject.isNull(resources.getString(R.string.rating)))
                    movie.setmRating(jsonObject.getDouble(resources.getString(R.string.rating)));
                if (!jsonObject.isNull(resources.getString(R.string.images))){
                    JSONObject jj = jsonObject.getJSONObject(resources.getString(R.string.images)).getJSONObject(resources.getString(R.string.poster));
                    if (!jj.isNull(resources.getString(R.string.thumb))){
                        movie.setmBanner(jj.getString(resources.getString(R.string.thumb)));
                    }
                }
                if (!jsonObject.isNull(resources.getString(R.string.genres))){
                    JSONArray jj = jsonObject.getJSONArray(resources.getString(R.string.genres));
                    movie.setmGenre(jj.toString());
                }

                if (!jsonObject.isNull(resources.getString(R.string.ids))){
                    JSONObject joID = jsonObject.getJSONObject(resources.getString(R.string.ids));
                    if (!joID.isNull(resources.getString(R.string.imdb)))
                        movie.setmIdIMDB(joID.getString(resources.getString(R.string.imdb)));
                    if (!joID.isNull(resources.getString(R.string.tmdb)))
                        movie.setmIdTMDB(joID.getString(resources.getString(R.string.tmdb)));
                    if (!joID.isNull(resources.getString(R.string.trakt)))
                        movie.setmTrakt(joID.getInt(resources.getString(R.string.trakt)));
                    if (!joID.isNull(resources.getString(R.string.slug)))
                        movie.setmSlug(joID.getString(resources.getString(R.string.slug)));
                }

                movieList.add(movie);
            }
            return movieList;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

