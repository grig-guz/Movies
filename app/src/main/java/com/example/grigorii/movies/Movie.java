package com.example.grigorii.movies;

/**
 * Created by grigorii on 29/06/16.
 * Class for keeping the data for a single movie entry
 */
public class Movie {

    private String mTitle;
    private String mPlotOverview;
    private float mUserRating;
    private String mPosterURL;

    public Movie(String title,
                 String plotOverview,
                 float userRating,
                 String posterURL) {

        mTitle = title;
        mPlotOverview = plotOverview;
        mUserRating = userRating;
        mPosterURL = posterURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPlotOverview() {
        return mPlotOverview;
    }

    public void setPlotOverview(String mPlotOverview) {
        this.mPlotOverview = mPlotOverview;
    }

    public float getUserRating() {
        return mUserRating;
    }

    public void setUserRating(float mUserRating) {
        this.mUserRating = mUserRating;
    }

    public String getPosterURL() {
        return mPosterURL;
    }

    public void setPosterURL(String mPosterURL) {
        this.mPosterURL = mPosterURL;
    }
}
