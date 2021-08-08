package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import com.panabey.smartnotebook.Database.SQLiteHelper;

public class Trash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        Button ButtonTestSQL = findViewById(R.id.buttonTestSQL);

        SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
        String test = "test";


        ButtonTestSQL.setOnClickListener(v -> new Thread(() -> {
            int i = 0;
            while (i < 10000) {
                sqLiteHelper.UploadInDatabaseNotes(database, test, test, test);
                i++;
            }
        }).start());
    }
}