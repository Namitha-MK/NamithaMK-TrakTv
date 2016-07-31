package com.trivago.traktv.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trivago.traktv.R;
import com.trivago.traktv.adapter.SearchPagerAdapter;
import com.trivago.traktv.bean.MovieBean;
import com.trivago.traktv.controller.LoadMovieListTask;
import com.trivago.traktv.listener.IMovieListLoadedListener;
import com.trivago.traktv.utils.Constants;
import com.trivago.traktv.utils.Helper;

import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class SearchListActivity extends ActionBarActivity implements  TextWatcher, IMovieListLoadedListener {

    private static final String TAG = "SearchListActivity";

    private int mPageNo = 1;
    private SearchPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private EditText mSearch;
    private  ArrayList<MovieBean> mMovieList;
    private LoadMovieListTask loadMoviesFromUrlTask;
    private ArrayList<ArrayList<MovieBean>> mMovieListPage = new ArrayList<>();
    private android.support.v4.view.PagerTitleStrip pagerTitleStrip;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        init();

    }


    private void init(){
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        pagerTitleStrip = (android.support.v4.view.PagerTitleStrip)findViewById(R.id.pager_title_strip);
        pagerTitleStrip.setVisibility(View.INVISIBLE);
        mViewPager.setVisibility(View.INVISIBLE);
        mSearch = (EditText) findViewById(R.id.et_serch_box);
        mMovieList = new  ArrayList<>();



        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                mPageNo = position + 1;
//                    if (stopSearch) {
                if (mMovieListPage.size() >= mPageNo && mMovieListPage.get(mPageNo - 1) != null) {
                    mMovieList.clear();
                    mMovieList.addAll(mMovieListPage.get(position));
                    mSectionsPagerAdapter.notifyDataSetChanged();
                } else
                    search(mSearch.getText().toString());
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setListViewAdapter();

        mSearch.addTextChangedListener(this);
    }




    private void setListViewAdapter() {
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SearchPagerAdapter(SearchListActivity.this, getSupportFragmentManager(),mMovieList);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    private LoadMovieListTask getDataFromUrl(String url) {
        return (LoadMovieListTask) new LoadMovieListTask(this, url, this).execute();
    }


    @Override
    public void onMoviesLoaded(ArrayList<MovieBean> movies) {
        pagerTitleStrip.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);

        try{
            if (mMovieListPage.size() < mPageNo)
                mMovieListPage.add(mPageNo-1,movies);
            else if (mMovieListPage.get(mPageNo) == null)
                mMovieListPage.add(mPageNo-1,movies);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }




        if (movies.size() < Constants.ITEMS_PER_PAGE){
            mMovieList.clear();
            mMovieList.addAll(movies);
            mSectionsPagerAdapter.setSize(mPageNo);
            mSectionsPagerAdapter.notifyDataSetChanged();
        }
        else if (movies.isEmpty()){
            mSectionsPagerAdapter.setSize(mPageNo - 1);
            if (mMovieListPage.isEmpty()){
                pagerTitleStrip.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
            }

        }else{
            mMovieList.clear();
            mMovieList.addAll(movies);
            mSectionsPagerAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        initializePagerValues();
        if(Helper.isOnline(this))
        search(s.toString());
        else
            Toast.makeText(this, getResources().getString(R.string.no_network),Toast.LENGTH_SHORT).show();

    }

    private void initializePagerValues(){
        mPageNo = 1;
        mViewPager.setVisibility(View.INVISIBLE);
        pagerTitleStrip.setVisibility(View.INVISIBLE);
        mMovieListPage.clear();
        mMovieList.clear();
        mViewPager.setCurrentItem(0);
        mSectionsPagerAdapter.setSize(100);
        mSectionsPagerAdapter.notifyDataSetChanged();
    }


    public void search(String s){


        String query = s.toString().replace(" ","%20");

        if(s.length()>=3){
            /*pagerTitleStrip.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);*/
            String url = Helper.getSearchURL(query, mPageNo);
            Log.e("URL", url.toString());

            if (loadMoviesFromUrlTask!=null && loadMoviesFromUrlTask.getStatus() == AsyncTask.Status.RUNNING){
                loadMoviesFromUrlTask.cancel(true);
            }
            loadMoviesFromUrlTask = getDataFromUrl(url);

        }
    }


}
