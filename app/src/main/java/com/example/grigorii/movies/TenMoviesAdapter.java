package com.example.grigorii.movies;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by grigorii on 28/06/16.
 */
public class TenMoviesAdapter extends ArrayAdapter<Movie> {

    private ArrayList<Movie> movies;

    public TenMoviesAdapter(Context context, int resource, ArrayList<Movie> moviesList) {
        super(context, resource, moviesList);
        movies = moviesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            v = layoutInflater.inflate(R.layout.grid_view_item, null);
        }
        Movie movie = getItem(position);

        if (movie != null) {

            ImageView moviePoster = (ImageView) v.findViewById(R.id.movie_img_grid);

            Picasso.with(getContext()).load(movie.getPosterURL())
                    .into(moviePoster);
        }

        return v;
    }

    public void setGridData(ArrayList<Movie> moviesList) {
        movies.clear();
        movies.addAll(moviesList);
        
        notifyDataSetChanged();
    }
}
