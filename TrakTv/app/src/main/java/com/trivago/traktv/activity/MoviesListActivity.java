package com.trivago.traktv.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.trivago.traktv.R;
import com.trivago.traktv.adapter.MovieListAdapter;
import com.trivago.traktv.bean.MovieBean;
import com.trivago.traktv.controller.LoadMovieListTask;
import com.trivago.traktv.listener.IMovieListLoadedListener;
import com.trivago.traktv.utils.Constants;
import com.trivago.traktv.utils.Helper;

import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class MoviesListActivity extends AppCompatActivity implements
         AdapterView.OnItemClickListener, IMovieListLoadedListener {

    private static final String TAG = "MoviesListActivity";
    private ListView mMoviesListView;
    private ProgressDialog mProgress;
    private MovieListAdapter mMovieListAdapter;
    public static ArrayList<MovieBean> mPopularMovieList;

    int mPageCount = Constants.MIN_PAGE_COUNT;
    int mMaxpages = Constants.MAX_PAGES;
    private Button mTryAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {


        mPopularMovieList = new ArrayList<>();

        mMoviesListView = (ListView) findViewById(R.id.list);
        mTryAgain = (Button)findViewById(R.id.btn_try_again);
        mTryAgain.setVisibility(View.GONE);
        mMoviesListView.setVisibility(View.VISIBLE);
        setButtonClickListener();

        if(Helper.isOnline(getApplicationContext())) {
            getDataFromUrl(Helper.getMoviesURL("popular", mPageCount));
            setListViewAdapter();
            mMoviesListView.setOnScrollListener(onScrollListener());
            mMoviesListView.setOnItemClickListener(this);
        }else {
            mTryAgain.setVisibility(View.VISIBLE);
            mMoviesListView.setVisibility(View.GONE);
        }
    }

    private void setButtonClickListener(){
        mTryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });
    }

    private void setListViewAdapter() {
        mMovieListAdapter = new MovieListAdapter(this, mPopularMovieList);
        mMoviesListView.setAdapter(mMovieListAdapter);
    }

    // calling asynctask to get json data from internet
    private void getDataFromUrl(String url) {
        new LoadMovieListTask(this, url, this).execute();
    }


    private AbsListView.OnScrollListener onScrollListener() {
        return new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                int threshold = 1;
                int count = mMoviesListView.getCount();

                if (scrollState == SCROLL_STATE_IDLE) {
                    if (mMoviesListView.getLastVisiblePosition() >= count - threshold && mPageCount < mMaxpages) {
                        Log.i(TAG, "loading more data");
                        mPageCount++;
                        // Execute LoadMoreDataTask AsyncTask
                        getDataFromUrl(Helper.getMoviesURL(Constants.TYPE_POPULAR_MOVIES, mPageCount));
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
            }

        };
    }

    @Override
    public void onMoviesLoaded(ArrayList<MovieBean> movies) {
        mPopularMovieList.addAll( movies);
        mMovieListAdapter.notifyDataSetChanged();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            Intent searchIntent = new Intent(MoviesListActivity.this, SearchListActivity.class);
            startActivity(searchIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
