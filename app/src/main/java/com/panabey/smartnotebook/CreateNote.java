package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.panabey.smartnotebook.Database.SQLiteHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CreateNote extends AppCompatActivity {

    private View editTextHead;
    private View editTextBody;

    private TextView lastModifiedDate;
    private TextView EditTextHeadTextView;
    private TextView editTextBodyTextView;
    private Toolbar toolbarCreateNote;
    private String lastModify;
    private DateFormat dateFormat;

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

         EditTextHeadTextView = findViewById(R.id.editTextHeadText);
         editTextBodyTextView = findViewById(R.id.editTextNotes);


        //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        toolbarCreateNote = findViewById(R.id.toolbarUpPanel);
        toolbarCreateNote.setNavigationOnClickListener(v -> {
            WriteSQL();
            Intent intentBackMenu = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intentBackMenu);
        });

        toolbarCreateNote.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.ImportItem:
                    break;
                case R.id.UndoItem:
                    break;
                case R.id.RedoItem:
                    break;
                default:
                    return false;
            }
            return true;
        });
        //------------------------Последние изменения в тексте---------------------------//
        editTextBody.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String lastModify = dateFormat.format(new Date());
                lastModifiedDate.setText("Последние изменения: " + lastModify);
            }
        });

        editTextHead.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String lastModify = dateFormat.format(new Date());
                lastModifiedDate.setText("Последние изменения: " + lastModify);
            }
        });

    }
    private String getDateTime() {
        Date dateBody = new Date();
        String lastModify = dateFormat.format(new Date());
        return lastModify;
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