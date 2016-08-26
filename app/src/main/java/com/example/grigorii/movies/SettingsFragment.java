package com.example.grigorii.movies;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;


/**
 * Created by grigorii on 04/07/16.
 */
public class SettingsFragment extends PreferenceFragment
        implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref_general);

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = o.toString();
        if (key.equals(getActivity().getResources().getString(R.string.pref_sort_order_key))) {

            ListPreference listPreference = (ListPreference) preference;

            int prefIndex = listPreference.findIndexOfValue(key);

            System.out.println(prefIndex);

            if (prefIndex >= 0) {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }

        return true;
    }

}
