package com.example.picapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

public class MyPreferenceFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        getActivity().findViewById(R.id.toolbar_main).setVisibility(View.VISIBLE);
        setPreferencesFromResource(R.xml.preferences, rootKey);
        applyListener(findPreference("dark_mode_switch"));
    }

    private void applyListener (Preference preference) {
        preference.setOnPreferenceChangeListener(listener);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());

        listener.onPreferenceChange(preference, PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getBoolean("dark_mode_switch", false));
    }

    private Preference.OnPreferenceChangeListener listener = (preference, newValue) -> {
        Boolean value = (boolean) newValue;

        if (preference instanceof SwitchPreference) {
            SwitchPreference pref = (SwitchPreference) preference;
            if (value) {
                 getActivity().setTheme(R.style.DarkMode);

            }else {
                getActivity().setTheme(R.style.AppTheme);
            }
        }
        return true;
    };
}
