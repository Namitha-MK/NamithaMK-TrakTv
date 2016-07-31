package com.trivago.traktv.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class Helper {

    public static String getMoviesURL(String category, int page) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.BASE_URL)
                .append(Constants.MOVIES)
                .append("/")
                .append(category)
                .append("?page=" + page)
                .append("&extended=full,images");


        return url.toString();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public static String getSearchURL(String keyword, int page) {
        StringBuilder url = new StringBuilder();
        url.append(Constants.BASE_URL)
                .append(Constants.TYPE_SEARCH)
                .append("?query=")
                .append(keyword)
                .append("&type=movie")
                .append("&page=" + page);

        return url.toString();
    }


}

