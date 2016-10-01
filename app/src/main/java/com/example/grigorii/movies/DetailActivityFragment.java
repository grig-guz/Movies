package com.example.grigorii.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    TextView titleView;
    TextView ratingView;
    TextView plotOverview;
    ImageView posterView;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        // Getting movie object from intent
        final Gson gson = new Gson();

        String movieJson = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);
        Movie movie = gson.fromJson(movieJson, Movie.class);

        titleView = (TextView) v.findViewById(R.id.movie_title);
        ratingView = (TextView) v.findViewById(R.id.movie_rating);
        plotOverview = (TextView) v.findViewById(R.id.movie_plot);
        posterView = (ImageView) v.findViewById(R.id.movie_poster);

        String title = movie.getTitle();
        String rating = movie.getUserRating() + "/10";
        String imageUrl = movie.getPosterURL();
        String plot = movie.getPlotOverview();

        Picasso.with(getActivity()).load(imageUrl).into(posterView);
        titleView.setText(title);
        ratingView.setText(rating);
        plotOverview.setText(plot);

        return v;
    }


}
