package com.trivago.traktv.adapter;

/**
 * Created by Namitha M K on 28/07/2016.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.trivago.traktv.bean.MovieBean;
import com.trivago.traktv.fragment.SearchListFragment;
import com.trivago.traktv.utils.Constants;

import java.util.ArrayList;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SearchPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private ArrayList<MovieBean> mPopularMovieList;
    private int mNoOfPages = 100;

    public SearchPagerAdapter(Context context, FragmentManager fm, ArrayList<MovieBean> popularMovieList) {
        super(fm);
        mContext = context;
        mPopularMovieList = popularMovieList;

    }

    @Override
    public Fragment getItem(int position) {
        return SearchListFragment.newInstance(mContext, position, mPopularMovieList);
    }

    @Override
    public int getCount() {
        return mNoOfPages;
    }

    public void setSize(int count){
        this.mNoOfPages = count;
    }

    //@Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public CharSequence getPageTitle(int position) {

        Integer currentPosition = position + 1;
        int upperLimit = currentPosition* Constants.ITEMS_PER_PAGE;
        int lowerLimit = ((currentPosition-1)*Constants.ITEMS_PER_PAGE)+1;

        String title = String.valueOf(lowerLimit) + "-" + String.valueOf(upperLimit);

        return title;
    }

}
