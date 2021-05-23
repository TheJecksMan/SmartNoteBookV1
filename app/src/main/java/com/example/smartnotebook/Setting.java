package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;

public class Setting extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;
    TextView sizeDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        /*
        sizeDB = findViewById(R.id.SizeDB);
        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
        database.getMaximumSize();
        File file = new File(database.getPath());

        sizeDB.setText("Размер базы данных" + file.length());

         */
    }
}