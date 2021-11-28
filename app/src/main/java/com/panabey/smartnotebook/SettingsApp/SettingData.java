package com.panabey.smartnotebook.SettingsApp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.R;

public class SettingData extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.setting_storage);
        InfoSize();
    }

    private void InfoSize(){
        Preference preferenceSizeDatabase = findPreference("key_sizeDatabase");

        SQLiteHelperKotlin sqLiteHelperKotlin = new SQLiteHelperKotlin(getContext());
        SQLiteDatabase database = sqLiteHelperKotlin.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT page_count * page_size as size FROM pragma_page_count, pragma_page_size;", null);
        try {
            cursor.moveToFirst();
            preferenceSizeDatabase.setSummary("Фактический размер: " +
                    cursor.getInt(cursor.getColumnIndex("size"))/1024 + "Мб");
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
