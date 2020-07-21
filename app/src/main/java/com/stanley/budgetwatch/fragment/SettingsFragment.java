package com.stanley.budgetwatch.fragment;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.stanley.budgetwatch.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        ListPreference listPreference = (ListPreference) findPreference(getResources().getString(R.string.jpegQuality));

    }
}
