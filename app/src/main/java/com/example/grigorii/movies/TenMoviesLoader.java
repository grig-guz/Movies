package com.example.grigorii.movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
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

/**
 * Created by grigorii on 28/06/16.
 *
 * Class for downloading data for 10 movies from OpenMovieDatabase.
 * It loads whether 10 most popular movies or 10 movies with highest
 * rating.
 */
public class TenMoviesLoader extends AsyncTask<Void, Void, Movie[]> {

    private static final String LOG_TAG = TenMoviesLoader.class.getSimpleName();

    private TenMoviesAdapter mMoviesAdapter;

    // Variable for deciding which kind of movie data to download
    private boolean mLoadByPopularity;

    // Constants for building URL
    public static final String TYPE_POPULAR = "popular";
    public static final String TYPE_TOP_RATED = "top_rated";
    private static final String APPID_PARAM = "app_id";
    private static final String APPKEY_PARAM = "app_key";

    public TenMoviesLoader(TenMoviesAdapter adapter, boolean loadByPopularity) {
        mMoviesAdapter = adapter;
        mLoadByPopularity =  loadByPopularity;
    }

    @Override
    protected Movie[] doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;

        BufferedReader reader = null;

        String queryResult = null;

        try {

            final String BASE_URI =
                    "https://http://api.themoviedb.org/3/movie/";

            Uri uri = Uri.parse(BASE_URI);

            if (mLoadByPopularity) {
                uri.buildUpon().appendPath(TYPE_POPULAR).build();
            } else uri.buildUpon().appendPath(TYPE_TOP_RATED).build();

            // TODO: Add your app key
            uri.buildUpon().appendQueryParameter(APPKEY_PARAM, "ADD APP KEY").build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
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

        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    private Movie[] getMoviesDataFromJson(String queryResult) throws JSONException {

        // Constants for parsing Movie objects from JSON
        final String RESULTS = "results";
        final String TITLE = "title";
        final String POSTER_URL = "poster_path";
        final String PLOT_OVERVIEW = "overview";
        final String RATING = "vote_average";

        JSONObject movieQuery = new JSONObject(queryResult);
        JSONArray moviesJson = movieQuery.getJSONArray(RESULTS);

        Movie[] movies = new Movie[10];

        for (int i = 0; i < 10; i++) {
            String title;
            String posterUrl;
            String plotOverview;
            float rating;

            JSONObject movieJson = moviesJson.getJSONObject(i);

            title = movieJson.getString(TITLE);
            posterUrl = movieJson.getString(POSTER_URL);
            plotOverview = movieJson.getString(PLOT_OVERVIEW);
            rating = (float) movieJson.getDouble(RATING);

            Movie movie = new Movie(title,
                    plotOverview,
                    rating,
                    posterUrl);

            movies[i] = movie;
        }

        return movies;
    }

    @Override
    protected void onPostExecute(Movie[] movies) {
        mMoviesAdapter.clear();
        for (Movie movie : movies) {
            mMoviesAdapter.add(movie);
        }
        super.onPostExecute(movies);
    }
}
