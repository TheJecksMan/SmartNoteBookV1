package com.example.smartnotebook.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.smartnotebook.R;

public class SettingsFragment extends PreferenceFragmentCompat  implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback{
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_main);
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
    /*
        final Bundle args = pref.getExtras();
        final Fragment fragment =  getActivity().getSupportFragmentManager().getFragmentFactory().instantiate(args.getClassLoader(), pref.getFragment());
        fragment.setArguments(args);
        fragment.setTargetFragment(caller, 0);

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPager, fragment)
                .commit();
        return true;

     */
        return false;
    }

}
