package com.panabey.smartnotebook;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.panabey.smartnotebook.Database.SQLiteHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateNote extends AppCompatActivity {

    private View editTextHead;
    private View editTextBody;
    private Button NotesButtonMenu;

    private TextView lastModifiedDate;
    private TextView EditTextHeadTextView;
    private TextView editTextBodyTextView;
    String lastModify;
    DateFormat dateFormat;

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

         EditTextHeadTextView = findViewById(R.id.editTextHeadText);
         editTextBodyTextView = findViewById(R.id.editTextNotes);

        Button button = findViewById(R.id.BackToNote);
        //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

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
                    String lastModify = dateFormat.format(new Date());
                    lastModifiedDate.setText("Последние изменения: " + lastModify);
                }
            }
        });

        editTextHead.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String lastModify = dateFormat.format(new Date());
                    lastModifiedDate.setText("Последние изменения: " + lastModify);
                }
            }
        });


    }
    private String getDateTime() {
        Date dateBody = new Date();
        String lastModify = dateFormat.format(dateBody);
        return lastModify;
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

    @Override
    public void onBackPressed() {
        WriteSQL();

        Intent intentBackNotes = new Intent(this, MainMenu.class);
        startActivity(intentBackNotes);
        finish();
    }

    private void WriteSQL(){
        if (EditTextHeadTextView.getText().length()  != 0)
        {
            contentValues.put(SQLiteHelper.KEY_HEAD_NOTES, EditTextHeadTextView.getText().toString());
            contentValues.put(SQLiteHelper.KEY_BODY_NOTES, editTextBodyTextView.getText().toString());
            contentValues.put(SQLiteHelper.KEY_DATETIME, getDateTime());

            database.insert(SQLiteHelper.TABLE_CONTACTS, null, contentValues);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lastModifiedDate = null;
    }
}