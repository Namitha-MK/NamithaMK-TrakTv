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
import com.trivago.traktv.fragment.SearchListFragment;

import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class SearchListAdapter extends BaseAdapter {


    Context context;
    ArrayList<MovieBean> mMovieBeanList;


    public SearchListAdapter(Context context, ArrayList<MovieBean> mMovieBeanList) {
        this.context = context;
        this.mMovieBeanList = mMovieBeanList;
    }




    @Override
    public int getCount() {
        return SearchListFragment.mPopularMovieList.size();
    }

    @Override
    public Object getItem(int position) {
        return SearchListFragment.mPopularMovieList.get(position);
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

        convertView = inflater.inflate(R.layout.search_result, parent, false);

        holder = new ViewHolder(convertView);

        convertView.setTag(holder);

        MovieBean MovieBean = (MovieBean) getItem(position);

        holder.title.setText(MovieBean.getTitle());
        holder.year.setText(" ("+MovieBean.getmYear() +")");
        holder.overview.setText(MovieBean.getmOverView());
        String mBanner = MovieBean.getmBanner();
        if (mBanner == null){
            holder.noImage.setVisibility(View.VISIBLE);
            holder.banner.setVisibility(View.GONE);
        }else {
            try {
                Picasso.with(context)
                        .load(mBanner).resize(150, 150)
                        //.centerCrop()
                        .into(holder.banner);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    private static class ViewHolder {

         ImageView banner;

         TextView title;
         TextView year;
         TextView overview;
         TextView noImage;

        public ViewHolder(View v) {
            title = (TextView) v.findViewById(R.id.tv_row_title);
            title.setSelected(true);
            year = (TextView) v.findViewById(R.id.tv_row_year);
            overview = (TextView) v.findViewById(R.id.tv_overview);
            noImage = (TextView) v.findViewById(R.id.no_image);
            overview.setSelected(true);
            banner = (ImageView) v.findViewById(R.id.iv_row_banner);

        }
    }
}
