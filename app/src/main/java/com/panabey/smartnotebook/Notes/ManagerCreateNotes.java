package com.panabey.smartnotebook.Notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ManagerCreateNotes {

    private final Context context;

    private List<String> ListTask;
    private List<Boolean> BooleanTask;
    private RecyclerAdapterList recyclerAdapterList;
    final RecyclerView recyclerView;

    public ManagerCreateNotes(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    private void CreateNote() {
        ListTask = new ArrayList<>();
        BooleanTask = new ArrayList<>();

        recyclerAdapterList = new RecyclerAdapterList(ListTask, BooleanTask);

        recyclerView.setAdapter(recyclerAdapterList);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 20);
    }

    /**
     * Навигация по RecyclerView.
     * Используется для перетаскивания и удаления заметок.
     */
    private void onTouch() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                Collections.swap(ListTask, fromPosition, toPosition);
                Collections.swap(BooleanTask, fromPosition, toPosition);

                recyclerAdapterList.notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    ListTask.remove(position);
                    BooleanTask.remove(position);
                    recyclerAdapterList.notifyItemRemoved(position);
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void ManagerRecyclerView() {
        CreateNote();
        onTouch();
    }

    public void onClickAddItem() {
        if (ListTask.isEmpty()) {
            CreateNote();
            recyclerAdapterList.notifyDataSetChanged();
        }
        ListTask.add("");
        BooleanTask.add(false);
        recyclerAdapterList.notifyItemInserted(ListTask.size() - 1);
        recyclerAdapterList.notifyItemInserted(BooleanTask.size() - 1);
    }

    /**
     * Загрузка подзадач из баз данных.
     */
    public void loadTaskFromDatabase(int IDNotes){

        SQLiteHelperKotlin sqLiteHelperKotlin = new SQLiteHelperKotlin(context);
        SQLiteDatabase database = sqLiteHelperKotlin.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Tasks where IDNotes = " + IDNotes, null);
        cursor.moveToFirst();

        for(int i = 0; i<cursor.getCount(); i++){
            ListTask.add(cursor.getString(cursor.getColumnIndex("Task")));
            BooleanTask.add(cursor.getInt(cursor.getColumnIndex("TaskBoolean")) == 1? true:false);
            recyclerAdapterList.notifyItemInserted(i);
            recyclerAdapterList.notifyItemInserted(i);
            cursor.moveToNext();
        }
        cursor.close();
       // recyclerAdapterList.notifyDataSetChanged();

    }

    /**
     * Перезапись и запись заметок из базы данных
     * для последующего использования.
     */
    public void writeInDatabaseNotes(TextView Head, TextView Body, Boolean clickNoteBoolean, List<String> ListTag, int ItemID){

        String checkedNullText = Head.getText().toString();
        if (checkedNullText.trim().isEmpty()){
            return;
        }

        SQLiteHelperKotlin sqLiteHelperKotlin = new SQLiteHelperKotlin(context);
        SQLiteDatabase database = sqLiteHelperKotlin.getWritableDatabase();

        if(!clickNoteBoolean){ //Запись
            int ItemIDWithDatabase = sqLiteHelperKotlin.getItemID(database);

            sqLiteHelperKotlin.insertNotesInDatabase(database,
                    Head.getText().toString(),Body.getText().toString(),getDateTime());

            for (int i = 0; i < ListTask.size(); i++){
                sqLiteHelperKotlin.insertTaskInDatabase(database, ItemIDWithDatabase, ListTask.get(i), BooleanTask.get(i)? 1: 0);
            }

            ListTag.removeAll(Arrays.asList("", null));

            for (int j = 0; j < ListTag.size(); j++){
                sqLiteHelperKotlin.insertTagInDatabase(database, ItemIDWithDatabase, ListTag.get(j));
            }
        }
        else{ //Перезапись
            sqLiteHelperKotlin.updateNotes(database,
                    Head.getText().toString(),Body.getText().toString(), getDateTime(),ItemID);

            sqLiteHelperKotlin.deleteTaskInDatabase(database, ItemID);
            for (int i = 0; i < ListTask.size(); i++){
                sqLiteHelperKotlin.insertTaskInDatabase(database, ItemID, ListTask.get(i), BooleanTask.get(i)? 1: 0);
            }
            ListTag.removeAll(Arrays.asList("", null));
            sqLiteHelperKotlin.deleteTafInDatabase(database, ItemID);
            for (int j = 0; j < ListTag.size(); j++){
                sqLiteHelperKotlin.insertTagInDatabase(database, ItemID, ListTag.get(j));
            }
        }
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        //DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
