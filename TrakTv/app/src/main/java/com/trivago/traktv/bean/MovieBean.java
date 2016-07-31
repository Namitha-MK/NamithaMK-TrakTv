package com.trivago.traktv.bean;

/**
 * Created by Namitha M K on 27/07/2016.
 */
public class MovieBean {


    private String title;
    private String mTagLine;
    private String mOverView;
    private String mTrailer;
    private String mHomePage;
    private String mBanner;
    private int mYear;
    private int mTrakt;
    private int mRunTime;
    double mRating;
    private String mIdIMDB;
    private String mIdTMDB;
    private String mSlug;
    private String mGenre;


    public MovieBean() {
        title = "";
        mYear = 0;
        mIdIMDB = "";
        mIdTMDB = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getmYear() {
        return mYear;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public String getmIdIMDB() {
        return mIdIMDB;
    }

    public void setmIdIMDB(String mIdIMDB) {
        this.mIdIMDB = mIdIMDB;
    }

    public String getmIdTMDB() {
        return mIdTMDB;
    }

    public void setmIdTMDB(String mIdTMDB) {
        this.mIdTMDB = mIdTMDB;
    }

    public int getmTrakt() {
        return mTrakt;
    }

    public void setmTrakt(int mTrakt) {
        this.mTrakt = mTrakt;
    }

    public String getmSlug() {
        return mSlug;
    }

    public void setmSlug(String mSlug) {
        this.mSlug = mSlug;
    }

    public String getmTagLine() {
        return mTagLine;
    }

    public void setmTagLine(String mTagLine) {
        this.mTagLine = mTagLine;
    }

    public String getmOverView() {
        return mOverView;
    }

    public void setmOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getmTrailer() {
        return mTrailer;
    }

    public void setmTrailer(String mTrailer) {
        this.mTrailer = mTrailer;
    }

    public String getmHomePage() {
        return mHomePage;
    }

    public void setmHomePage(String mHomePage) {
        this.mHomePage = mHomePage;
    }

    public String getmBanner() {
        return mBanner;
    }

    public void setmBanner(String mBanner) {
        this.mBanner = mBanner;
    }

    public int getmRunTime() {
        return mRunTime;
    }

    public void setmRunTime(int mRunTime) {
        this.mRunTime = mRunTime;
    }

    public double getmRating() {
        return mRating;
    }

    public void setmRating(double mRating) {
        this.mRating = mRating;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

}

