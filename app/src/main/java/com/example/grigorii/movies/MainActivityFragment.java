package com.example.grigorii.movies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

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


        TenMoviesLoader loader = buildLoader();
        loader.execute();

        final Gson gson = new Gson();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Movie movie = data.get(i);
                String movieJson = gson.toJson(movie);
                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieJson);

                startActivity(intent);
            }
        });

        return v;
    }

    public void updateMovieGrid() {

        TenMoviesLoader loader = buildLoader();
        loader.execute();
    }

    public TenMoviesLoader buildLoader() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortType = preferences.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_popular)
        );

        boolean sortByPopularity = true;

        if (sortType.equals(getString(R.string.pref_sort_order_top10))) {
            sortByPopularity = false;
        }

        return new TenMoviesLoader(adapter, sortByPopularity);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieGrid();
    }
}
