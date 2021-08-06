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
import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


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
    private int ItemID;

    //список подзадач
    List<String> List;
    private RecyclerAdapterList recyclerAdapterList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();

        //список подзадач
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Context context = this;
        List = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerViewList);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List.add("Новая подзадача");
                recyclerAdapterList = new RecyclerAdapterList(List, context);

                recyclerView.setAdapter(recyclerAdapterList);
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,20);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        editTextHead = findViewById(R.id.editTextHeadText);
        editTextBody= findViewById(R.id.editTextNotes);

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
            }
        });

        editTextHead.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                String lastModify = dateFormat.format(new Date());
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
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(List, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                List.remove(position);

                recyclerView.getAdapter().notifyItemRemoved(position);
               // recyclerAdapterList.notifyDataSetChanged();
            }
        }
    };
}