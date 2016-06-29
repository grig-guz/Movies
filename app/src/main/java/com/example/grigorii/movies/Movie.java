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


}
