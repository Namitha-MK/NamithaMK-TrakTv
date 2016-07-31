package com.trivago.traktv.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.trivago.traktv.R;
import com.trivago.traktv.bean.MovieBean;

import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class MovieListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MovieBean> mPopularMovieList;


    public MovieListAdapter(Context context, ArrayList<MovieBean> mMovieList) {
        this.context = context;
        this.mPopularMovieList = mMovieList;
    }




    @Override
    public int getCount() {
        return mPopularMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPopularMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.popular_movie_row, parent, false);
        // get all UI view
        holder = new ViewHolder(convertView);
        // set tag for holder
        convertView.setTag(holder);

        MovieBean movie = (MovieBean) getItem(position);

        holder.title.setText(movie.getTitle());
        holder.year.setText(""+movie.getmYear());
        holder.rating.setText((context.getResources().getString(R.string.rating_title))+(String.valueOf(movie.getmRating()).substring(0,3)));
        String genre = movie.getmGenre();
        if(genre!=null && !genre.equals("")){
            if (genre.contains(context.getResources().getString(R.string.genre_action)))
                holder.tv_action.setVisibility(View.VISIBLE);
            else
                holder.tv_action.setVisibility(View.GONE);
            if (genre.contains(context.getResources().getString(R.string.genre_adventure)))
                holder.tv_adventure.setVisibility(View.VISIBLE);
            if (genre.contains(context.getResources().getString(R.string.genre_fantasy)))
                holder.tv_fantancy.setVisibility(View.VISIBLE);
            if (genre.contains(context.getResources().getString(R.string.genre_science_fiction)))
                holder.tv_sience_fiction.setVisibility(View.VISIBLE);
            if (genre.contains(context.getResources().getString(R.string.genre_drama)))
                holder.tv_drama.setVisibility(View.VISIBLE);
            if (genre.contains(context.getResources().getString(R.string.genre_comedy)))
                holder.tv_comedy.setVisibility(View.VISIBLE);
            if (genre.contains(context.getResources().getString(R.string.genre_romance)))
                holder.tv_romance.setVisibility(View.VISIBLE);
        }

        try{
            Picasso.with(context)
                    .load(movie.getmBanner()).resize(150, 150)
                    //.centerCrop()
                    .into(holder.banner);
        }catch (Exception e){ e.printStackTrace(); }
        return convertView;
    }

    private static class ViewHolder {
        private TextView title,year,rating;
        private ImageView banner;

        //tags genre
        private TextView tv_action,tv_comedy,tv_drama,tv_adventure,tv_sience_fiction,tv_romance,tv_fantancy;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.tv_row_title);
            year = (TextView) v.findViewById(R.id.tv_row_year);
            rating = (TextView) v.findViewById(R.id.tv_row_rating);
            banner = (ImageView) v.findViewById(R.id.iv_row_banner);
            tv_action = (TextView) v.findViewById(R.id.action);
            tv_comedy = (TextView) v.findViewById(R.id.comedy);
            tv_drama = (TextView) v.findViewById(R.id.drama);tv_adventure = (TextView) v.findViewById(R.id.adventure);
            tv_sience_fiction = (TextView) v.findViewById(R.id.sience_fiction);
            tv_romance = (TextView) v.findViewById(R.id.romance);
            tv_fantancy = (TextView) v.findViewById(R.id.fantasy);
        }
    }

}
