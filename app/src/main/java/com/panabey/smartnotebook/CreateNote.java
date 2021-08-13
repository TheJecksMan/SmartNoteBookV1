package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panabey.smartnotebook.Database.SQLiteHelper;
import com.panabey.smartnotebook.Notes.Fab_Button.FabButtonManager;
import com.panabey.smartnotebook.Notes.ManagerCreateNotes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNote extends AppCompatActivity {

    private TextView EditTextHeadTextView;
    private TextView EditTextBodyTextView;
    private DateFormat dateFormat;

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase database;

    private boolean clickNoteBoolean;
    private int ItemID;

    ManagerCreateNotes managerCreateNotes;
    FabButtonManager FAB;

    //список подзадач
    RecyclerView recyclerView;

    private FloatingActionButton fab_main, fab1_mail, fab2_share;
    private TextView textview_mail, textview_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerViewList);

        managerCreateNotes = new ManagerCreateNotes(this, recyclerView);
        managerCreateNotes.ManagerRecyclerView();



        EditTextHeadTextView = findViewById(R.id.editTextHeadText);
        EditTextBodyTextView = findViewById(R.id.editTextNotes);

        Bundle ClickNote = getIntent().getExtras();

        new Thread(() -> {
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
        }).start();

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
        fab_main = findViewById(R.id.fab);
        fab1_mail = findViewById(R.id.fab1);
        fab2_share = findViewById(R.id.fab2);

        textview_mail = (TextView) findViewById(R.id.textview_mail);
        textview_share = (TextView) findViewById(R.id.textview_share);

        FAB = new FabButtonManager(fab_main, fab1_mail, fab2_share,textview_mail, textview_mail, getApplicationContext());
        FAB.FabOnClicked();

        fab1_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                managerCreateNotes.onClickAddItem();
            }
        });

    }
    private String getDateTime() {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
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

            //проверка на пустую строку
            String CheckedNullText = EditTextHeadTextView.getText().toString();
            if (CheckedNullText.trim().isEmpty()) {
                CheckedNullText = null;
                return;
            }

            //запись в базу данных
            if (!clickNoteBoolean) {
                //Новая заметка
                sqLiteHelper.UploadInDatabaseNotes(database,
                        EditTextHeadTextView.getText().toString(),
                        EditTextBodyTextView.getText().toString(),
                        getDateTime(),
                        managerCreateNotes.WriteAndUpdateTask(),
                        managerCreateNotes.WriteAndUpdateTaskBoolean());

            }
            else {
                //изменение заметки (перезапись)
                sqLiteHelper.UpdateNotes(database,
                        EditTextHeadTextView.getText().toString(),
                        EditTextBodyTextView.getText().toString(),
                        getDateTime(),
                        ItemID);
            }

            managerCreateNotes.WriteAndUpdateTask();

        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}