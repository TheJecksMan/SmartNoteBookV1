package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
    private TextView EditTextBodyTextView;
    private Toolbar toolbarCreateNote;
    private String lastModify;
    private DateFormat dateFormat;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase database;

    private boolean clickNoteBoolean;
    private String ItemID;
    String InfoDate = "Последние изменения: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();


        editTextHead = findViewById(R.id.editTextHeadText);
        editTextBody= findViewById(R.id.editTextNotes);
        lastModifiedDate = findViewById(R.id.lastModifiedDate);

        EditTextHeadTextView = findViewById(R.id.editTextHeadText);
        EditTextBodyTextView = findViewById(R.id.editTextNotes);

        Bundle ClickNote = getIntent().getExtras();

        //Изменения сообщения после нажатия
        if(ClickNote != null) {
            clickNoteBoolean = ClickNote.getBoolean("BooleanClickRecyclerView");
             if(clickNoteBoolean){
                 ItemID = ClickNote.getString("Id");

                 Cursor cursor = database.rawQuery("SELECT * FROM contactsNotes WHERE ID  = " + ItemID, null);
                 cursor.moveToFirst();

                 EditTextHeadTextView.setText(cursor.getString(cursor.getColumnIndex("HeadNotes")));
                 EditTextBodyTextView.setText(cursor.getString(cursor.getColumnIndex("BodyNotes")));
                 lastModifiedDate.setText(InfoDate + cursor.getString(cursor.getColumnIndex("DateTime")));

             }

        }

        //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        toolbarCreateNote = findViewById(R.id.toolbarUpPanel);
        toolbarCreateNote.setNavigationOnClickListener(v -> {

            WriteSQLAndUpdate();
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
                lastModifiedDate.setText(InfoDate + lastModify);
            }
        });

        editTextHead.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String lastModify = dateFormat.format(new Date());
                lastModifiedDate.setText(InfoDate + lastModify);
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
        WriteSQLAndUpdate();

        Intent intentBackNotes = new Intent(this, MainMenu.class);
        startActivity(intentBackNotes);
        finish();
    }

    private void WriteSQLAndUpdate(){
        new Thread(new Runnable() {
            public void run() {

                //запись в базу данных
                if (clickNoteBoolean != true) {
                    sqLiteHelper.UploadInDatabaseNotes(database, EditTextHeadTextView.getText().toString(),
                            EditTextBodyTextView.getText().toString(),
                            getDateTime());
                } else {
                    sqLiteHelper.UpdateNotes(database, EditTextHeadTextView.getText().toString(),
                            EditTextBodyTextView.getText().toString(),
                            getDateTime(),
                            ItemID);
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lastModifiedDate = null;
        System.gc();
    }
}