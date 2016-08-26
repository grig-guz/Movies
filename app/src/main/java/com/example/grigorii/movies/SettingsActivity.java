package com.example.grigorii.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by grigorii on 04/07/16.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preference);

        getFragmentManager().beginTransaction()
                .replace(R.id.preference, new SettingsFragment())
                .commit();
    }
}
