package com.trivago.traktv.fragment;

/**
 * Created by Namitha M K on 28/07/2016.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.trivago.traktv.R;
import com.trivago.traktv.adapter.SearchListAdapter;
import com.trivago.traktv.bean.DetailBean;
import com.trivago.traktv.bean.MovieBean;

import java.util.ArrayList;

public  class SearchListFragment extends Fragment {


    private static final String TAG = "SearchListFragment";
    public  ListView mMoviesListView;

    private SearchListAdapter mMovieListAdapter;
    private static Context mContext;
    public  static ArrayList<MovieBean> mPopularMovieList;





    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public  static SearchListFragment newInstance(Context context, int sectionNumber, ArrayList<MovieBean> popularMovieList ) {

        SearchListFragment fragment = new SearchListFragment();
        mPopularMovieList = popularMovieList;
        mContext = context;

        return fragment;
    }

    public SearchListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        mMoviesListView = (ListView) rootView.findViewById(R.id.list);

        if (mPopularMovieList == null)
        mPopularMovieList = new ArrayList<>();
        setListViewAdapter();
        setListItemListener();
    }

    private void setListViewAdapter() {
        mMovieListAdapter = new SearchListAdapter(mContext, mPopularMovieList);
        mMoviesListView.setAdapter(mMovieListAdapter);
        mMovieListAdapter.notifyDataSetChanged();
    }
    public  void setListItemListener() {
        mMoviesListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                MovieBean movieBean = (MovieBean) adapter.getItemAtPosition(position);
                DetailBean detailBean = new DetailBean();
                detailBean.setmTitle(movieBean.getTitle());
                detailBean.setmYear(String.valueOf(movieBean.getmYear()));
                detailBean.setmOverview(movieBean.getmOverView());
                detailBean.setmImageUrl(movieBean.getmBanner());
                showDialog(detailBean);


            }
        });

    }

    public  void showDialog(DetailBean detailBean){

        DetailFragment dialog = DetailFragment.newInstance(detailBean);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        dialog.show(fragmentManager, "dialog");
    }


}



