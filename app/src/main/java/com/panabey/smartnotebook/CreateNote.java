package com.panabey.smartnotebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panabey.smartnotebook.Database.SQLiteHelper;
import com.panabey.smartnotebook.Notes.Fab_Button.FabButtonManager;
import com.panabey.smartnotebook.Notes.FileManager;
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

    private Context context;

    private FloatingActionButton fab_main, fab1_task, fab2_attachments;
    private TextView textview_task, textview_attachments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        sqLiteHelper = new SQLiteHelper(this);
        database = sqLiteHelper.getWritableDatabase();

        recyclerView = findViewById(R.id.recyclerViewList);

        managerCreateNotes = new ManagerCreateNotes(this, recyclerView);
        managerCreateNotes.ManagerRecyclerView();

        context = getApplicationContext();
        /**
         * Управление файлового менеджера для работы с изображением для их хранения.
         */
        FileManager fileManager = new FileManager(context);

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
            Intent intentBackMenu = new Intent(context, MainMenu.class);
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
        fab1_task = findViewById(R.id.fab1);
        fab2_attachments = findViewById(R.id.fab2);

        textview_task = (TextView) findViewById(R.id.textview_task);
        textview_attachments = (TextView) findViewById(R.id.textview_attachments);

        FAB = new FabButtonManager(fab_main, fab1_task, fab2_attachments,textview_task, textview_attachments, getApplicationContext());
        FAB.FabOnClicked();

        fab1_task.setOnClickListener(view -> managerCreateNotes.onClickAddItem());
        fab2_attachments.setOnClickListener(view -> ToOpenWith());
    }

    private void ToOpenWith(){ //Открытие файлового менеджера
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivity(intent);
        onActivityResult(RESULT_OK,100, intent);
    }

    private String getDateTime() {
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date dateBody = new Date();
        return dateFormat.format(new Date());
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && requestCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                String uriString = data.getDataString();
            }
        }

    }
    @Override
    public void onBackPressed() {
        WriteSQLAndUpdate();

        Intent intentBackNotes = new Intent(this, MainMenu.class);
        startActivity(intentBackNotes);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

}