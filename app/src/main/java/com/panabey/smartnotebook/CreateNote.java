package com.panabey.smartnotebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.panabey.smartnotebook.Database.SQLiteHelper;
import com.panabey.smartnotebook.Notes.NotesRecyclerManager;
import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class CreateNote extends AppCompatActivity {

    private TextView EditTextHeadTextView;
    private TextView EditTextBodyTextView;
    private DateFormat dateFormat;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase database;

    private boolean clickNoteBoolean;
    private int ItemID;

    //список подзадач
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        getWindow().setBackgroundDrawable(null);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();

        Button buttonAdd = findViewById(R.id.buttonAdd);

        recyclerView = findViewById(R.id.recyclerViewList);
        //список подзадач
        NotesRecyclerManager notesRecyclerManager = new NotesRecyclerManager(this, recyclerView);
        notesRecyclerManager.ManagerCreateNote();

        buttonAdd.setOnClickListener(v -> {
            notesRecyclerManager.TaskListOnClick();
        });


        EditTextHeadTextView = findViewById(R.id.editTextHeadText);
        EditTextBodyTextView = findViewById(R.id.editTextNotes);

        Bundle ClickNote = getIntent().getExtras();

        //Изменения сообщения после нажатия
        if(ClickNote != null) {
            clickNoteBoolean = ClickNote.getBoolean("BooleanClickRecyclerView");
             if(clickNoteBoolean){
                 ItemID = ClickNote.getInt("Id");

                 Cursor cursor = database.rawQuery("SELECT * FROM contactsNotes WHERE ID  = " + ItemID, null);
                 cursor.moveToFirst();

                 EditTextHeadTextView.setText(cursor.getString(cursor.getColumnIndex("HeadNotes")));
                 EditTextBodyTextView.setText(cursor.getString(cursor.getColumnIndex("BodyNotes")));
             }
        }

        //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        Toolbar toolbarCreateNote = findViewById(R.id.toolbarUpPanel);
        toolbarCreateNote.setNavigationOnClickListener(v -> {

            WriteSQLAndUpdate();
            Intent intentBackMenu = new Intent(getApplicationContext(), MainMenu.class);
            startActivity(intentBackMenu);
        });

        /*
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
         */

        //------------------------Последние изменения в тексте---------------------------//
    }
    private String getDateTime() {
        Date dateBody = new Date();
        return dateFormat.format(new Date());
    }

    @Override
    public void onBackPressed() {
        WriteSQLAndUpdate();

        Intent intentBackNotes = new Intent(this, MainMenu.class);
        startActivity(intentBackNotes);
        finish();
    }

    private void WriteSQLAndUpdate(){
        new Thread(() -> {

            //запись в базу данных
            if (!clickNoteBoolean) {
                //Новая заметка
                sqLiteHelper.UploadInDatabaseNotes(database,
                        EditTextHeadTextView.getText().toString(),
                        EditTextBodyTextView.getText().toString(),
                        getDateTime());
            } else {
                //изменение заметки (перезапись)
                sqLiteHelper.UpdateNotes(database,
                        EditTextHeadTextView.getText().toString(),
                        EditTextBodyTextView.getText().toString(),
                        getDateTime(),
                        ItemID);
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}