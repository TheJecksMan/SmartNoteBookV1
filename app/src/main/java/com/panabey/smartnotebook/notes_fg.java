package com.panabey.smartnotebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.util.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс фрагмента.
 * Используется только для отображения заметок на главном блоке.
 */
public class notes_fg extends Fragment {

    SQLiteHelperKotlin sqLiteHelperKotlin;
    SQLiteDatabase database;

    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    List<String> NotesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes_fg, null);

        NotesList = new ArrayList<>();
        /**
         * Выгрузка всех заметок в массив,
         * который используется в списоке RecyclerView.
         */
        new Thread(new Runnable() {
            public void run() {
                sqLiteHelperKotlin = new SQLiteHelperKotlin(getContext());
                database = sqLiteHelperKotlin.getReadableDatabase();

                    Cursor cursor = database.rawQuery("SELECT * FROM Notes", null);
                try {
                    String temp = null;
                    cursor.moveToFirst();
                    if (cursor.getCount() > 0) {
                        do {
                            temp = cursor.getString(cursor.getColumnIndex("HeadNotes"));
                            if (temp != null) {
                                NotesList.add(temp);
                            }
                        } while (cursor.moveToNext());
                    }
                }
                finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }).start();
        //-----------------------------------------------------

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(NotesList, getContext());

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,10);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Button button = v.findViewById(R.id.buttonCreateNotes);

        button.setOnClickListener(v1 -> {
            Intent intentCreateNote = new Intent(getContext(), CreateNote.class);
            startActivity(intentCreateNote);
        });
        return v;
    }

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN , ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(NotesList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            sqLiteHelperKotlin = new SQLiteHelperKotlin(getContext());
            database = sqLiteHelperKotlin.getWritableDatabase();
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                NotesList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);

                SQLiteDatabase databaseDelete = sqLiteHelperKotlin.getWritableDatabase();
                sqLiteHelperKotlin.deleteNote(databaseDelete, position + 1);
            }
        }

    };
}


