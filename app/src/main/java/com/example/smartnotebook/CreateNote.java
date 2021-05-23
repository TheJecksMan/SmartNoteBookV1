package com.example.smartnotebook;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

public class CreateNote extends AppCompatActivity {

    private View editTextHead;
    private View editTextBody;
    private Button NotesButtonMenu;

    private TextView lastModifiedDate;
    private TextView editTextHeadTextView;
    private TextView editTextBodyTextView;

    SQLiteHelper sqLiteHelper;
    ContentValues contentValues;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();
        contentValues = new ContentValues();
        
         editTextHead = findViewById(R.id.editTextHeadText);
         editTextBody= findViewById(R.id.editTextNotes);
         lastModifiedDate = findViewById(R.id.lastModifiedDate);

         NotesButtonMenu = findViewById(R.id.NotesButtonMenu);

         editTextHeadTextView = findViewById(R.id.editTextHeadText);
         editTextBodyTextView = findViewById(R.id.editTextNotes);

        Button button = findViewById(R.id.BackToNote);

         //Кнопка открытия меню
        NotesButtonMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPopup(v);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WriteSQL();
                Intent intentBackMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intentBackMenu);
            }
        });

        //------------------------Последние изменения в тексте---------------------------//
        editTextBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Date dateBody = new Date();
                    lastModifiedDate.setText("Последние изменения:\n" + dateBody.toString());
                }
            }
        });

        editTextHead.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Date dateHead = new Date();
                    lastModifiedDate.setText("Последние изменения:\n" + dateHead.toString());
                }
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.getMenuInflater().inflate(R.menu.notes_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ImportItem:

                        return true;
                    default:
                        return false;
                }

            }
        });
        popup.show();
    }


    private void WriteSQL(){
        if (editTextHeadTextView.getText().toString() != null)
        {
            contentValues.put(sqLiteHelper.KEY_HEAD_NOTES, editTextHeadTextView.getText().toString());
            contentValues.put(sqLiteHelper.KEY_BODY_NOTES, editTextBodyTextView.getText().toString());

            database.insert(sqLiteHelper.TABLE_CONTACTS, null, contentValues);
        }
    }
    @Override
    public void onBackPressed() {

        WriteSQL();
        Intent intentBackNotes = new Intent(this, MainMenu.class);
        startActivity(intentBackNotes);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lastModifiedDate = null;
    }
}