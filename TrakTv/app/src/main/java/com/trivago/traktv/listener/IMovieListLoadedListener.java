package com.trivago.traktv.listener;

import com.trivago.traktv.bean.MovieBean;

import java.util.ArrayList;

/**
 * Created by Namitha M K on 27/07/2016.
 */

    public interface IMovieListLoadedListener {

        void onMoviesLoaded(ArrayList<MovieBean> movies);

    }


