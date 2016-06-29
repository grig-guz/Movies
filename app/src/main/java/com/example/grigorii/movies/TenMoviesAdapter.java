package com.example.grigorii.movies;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by grigorii on 28/06/16.
 */
public class TenMoviesAdapter extends ArrayAdapter<Movie> {

    public TenMoviesAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
