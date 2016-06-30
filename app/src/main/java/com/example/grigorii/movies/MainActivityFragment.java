package com.example.grigorii.movies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private TenMoviesAdapter adapter;
    private ArrayList<Movie> data;
    private GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        data = new ArrayList<>();
        gridView = (GridView) v.findViewById(R.id.movies_grid);
        adapter = new TenMoviesAdapter(getContext(), R.layout.grid_view_item, data);
        gridView.setAdapter(adapter);

        TenMoviesLoader loader = new TenMoviesLoader(adapter, true);
        loader.execute();

        return v;
    }
}
