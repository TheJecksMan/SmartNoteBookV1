package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.Notes.Fab_Button.FabButtonManager;
import com.panabey.smartnotebook.Notes.FileManager;
import com.panabey.smartnotebook.Notes.ManagerCreateNotes;

/**
 * Класс для работы заметок.
 */
public class CreateNote extends AppCompatActivity {

    private TextView EditTextHeadTextView;
    private TextView EditTextBodyTextView;

    private boolean clickNoteBoolean;
    private int ItemID;

    private ManagerCreateNotes managerCreateNotes;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        context = getApplicationContext();

        SQLiteHelperKotlin sqLiteHelperKotlin = new SQLiteHelperKotlin(this);
        SQLiteDatabase database = sqLiteHelperKotlin.getWritableDatabase();

        //список подзадач
        RecyclerView recyclerView = findViewById(R.id.recyclerViewList);

        managerCreateNotes = new ManagerCreateNotes(this, recyclerView);
        managerCreateNotes.ManagerRecyclerView();

        EditTextHeadTextView = findViewById(R.id.editTextHeadText);
        EditTextBodyTextView = findViewById(R.id.editTextNotes);

        Bundle ClickNote = getIntent().getExtras();

        /**
         * Выгрузка заметки по нажатию (ID).
         * ID получаем от RecyclerView.
         */

        //Изменения сообщения после нажатия
        if(ClickNote != null) {
            clickNoteBoolean = ClickNote.getBoolean("BooleanClickRecyclerView");
            if(clickNoteBoolean){
                ItemID = ClickNote.getInt("Id");
                Cursor cursor = database.rawQuery("SELECT * FROM Notes WHERE IDNotes  = " + ItemID, null);
                cursor.moveToFirst();

                EditTextHeadTextView.setText(cursor.getString(cursor.getColumnIndex("HeadNotes")));
                EditTextBodyTextView.setText(cursor.getString(cursor.getColumnIndex("BodyNotes")));
                cursor.close();
            }
        }

        managerCreateNotes.loadTaskFromDatabase(ItemID);

        /**
         * Верхняя панель взаиможействия.
         * Испульзутеся для взаимодействия с текстом и Activity.
         */
        Toolbar toolbarCreateNote = findViewById(R.id.toolbarUpPanel);
        toolbarCreateNote.setNavigationOnClickListener(v -> {

            WriteSQLAndUpdate();
        });

        FloatingActionButton fab_main = findViewById(R.id.fab);
        FloatingActionButton fab1_task = findViewById(R.id.fab1);
        FloatingActionButton fab2_attachments = findViewById(R.id.fab2);

        TextView textview_task = (TextView) findViewById(R.id.textview_task);
        TextView textview_attachments = (TextView) findViewById(R.id.textview_attachments);

        FabButtonManager FAB = new FabButtonManager(fab_main, fab1_task, fab2_attachments, textview_task, textview_attachments, getApplicationContext());
        FAB.FabOnClicked();

        fab1_task.setOnClickListener(view -> managerCreateNotes.onClickAddItem());

        /**
         * Открытие файлового менеджера Android
         * и последующая обработка
         */
        FileManager fileManager = new FileManager(getApplicationContext(), fab2_attachments);
        fileManager.OnClickFileManager();
    }

    private void WriteSQLAndUpdate(){
        new MaterialAlertDialogBuilder(CreateNote.this)
                .setTitle("Внимание!")
                .setMessage("Вы действительно хотите сохранить изменения?")
                .setPositiveButton(getString(R.string.DialogYes), (dialogInterface, i) -> {
                    managerCreateNotes.writeInDatabaseNotes(EditTextHeadTextView, EditTextBodyTextView, clickNoteBoolean, ItemID);
                    Intent intentBackNotes = new Intent(CreateNote.this, MainMenu.class);
                    startActivity(intentBackNotes);
                    finish();
                })
                .setNegativeButton(getString(R.string.DialogNo), (dialogInterface, i) -> {
                    Intent intentBackNotes = new Intent(CreateNote.this, MainMenu.class);
                    startActivity(intentBackNotes);
                    finish();
                })
                .setNeutralButton(getString(R.string.DialogCancel), (dialogInterface, i) -> {
                    return;
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        WriteSQLAndUpdate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}