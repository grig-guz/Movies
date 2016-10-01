package com.example.grigorii.movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by grigorii on 28/06/16.
 *
 * Class for downloading data for 10 movies from OpenMovieDatabase.
 * It loads either 10 most popular movies or 10 movies with highest
 * rating.
 */
public class TenMoviesLoader extends AsyncTask<Void, Void, ArrayList<Movie>> {

    // Constants for building URL
    public static final String TYPE_POPULAR = "popular";
    public static final String TYPE_TOP_RATED = "top_rated";
    private static final String LOG_TAG = TenMoviesLoader.class.getSimpleName();
    private static final String APPKEY_PARAM = "api_key";
    private TenMoviesAdapter mMoviesAdapter;
    // Variable for deciding which kind of movie data to download
    private boolean mLoadByPopularity;

    public TenMoviesLoader(TenMoviesAdapter adapter, boolean loadByPopularity) {
        mMoviesAdapter = adapter;
        mLoadByPopularity =  loadByPopularity;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;

        BufferedReader reader;

        String queryResult;

        try {

            final String BASE_URI =
                    "http://api.themoviedb.org/3/movie/";

            Uri uri = Uri.parse(BASE_URI);

            if (mLoadByPopularity) {
                uri = uri.buildUpon().appendPath(TYPE_POPULAR)
                        .appendQueryParameter(APPKEY_PARAM, BuildConfig.MOVIES_APP_ID).build();
            } else uri = uri.buildUpon().appendPath(TYPE_TOP_RATED)
                    .appendQueryParameter(APPKEY_PARAM, BuildConfig.MOVIES_APP_ID).build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {

                Log.e(LOG_TAG, "Request method returned empty stream");
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                builder.append(line);
            }

            if (builder.length() == 0) {

                Log.e(LOG_TAG, "Request method returned empty stream");
                return null;
            }

            queryResult = builder.toString();

            return getMoviesDataFromJson(queryResult);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {

            try {
                urlConnection.disconnect();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private ArrayList<Movie> getMoviesDataFromJson(String queryResult) throws JSONException {

        // Constants for parsing Movie objects from JSON
        final String RESULTS = "results";
        final String TITLE = "title";
        final String POSTER_URL = "poster_path";
        final String PLOT_OVERVIEW = "overview";
        final String RATING = "vote_average";
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";


        JSONObject movieQuery = new JSONObject(queryResult);
        JSONArray moviesJson = movieQuery.getJSONArray(RESULTS);

        ArrayList<Movie> movies = new ArrayList<>(10);

        for (int i = 0; i < 10; i++) {
            String title;
            String posterUrl;
            String plotOverview;
            String rating;

            JSONObject movieJson = moviesJson.getJSONObject(i);

            title = movieJson.getString(TITLE);
            posterUrl = movieJson.getString(POSTER_URL);
            plotOverview = movieJson.getString(PLOT_OVERVIEW);
            rating = movieJson.getString(RATING);

            Movie movie = new Movie(title,
                    plotOverview,
                    rating,
                    BASE_IMAGE_URL + posterUrl);

            movies.add(movie);
        }

        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {

        mMoviesAdapter.setGridData(movies);
        super.onPostExecute(movies);
    }
}
