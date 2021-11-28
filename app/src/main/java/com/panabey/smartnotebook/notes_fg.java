package com.panabey.smartnotebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.util.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс фрагмента Заметок.
 * Используется только для отображения заметок на главном блоке.
 */
public class notes_fg extends Fragment {

    private SQLiteHelperKotlin sqLiteHelperKotlin;
    private SQLiteDatabase database;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    private List<Integer> NotesID;
    private List<String> NotesList;
    private List<String> DateTimeList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes_fg, null);

        NotesID = new ArrayList<>();
        NotesList = new ArrayList<>();
        DateTimeList = new ArrayList<>();

        /**
         * Выгрузка всех заметок в массив,
         * который используется в списоке RecyclerView.
         */
        new Thread(() -> {
            sqLiteHelperKotlin = new SQLiteHelperKotlin(getContext());
            database = sqLiteHelperKotlin.getReadableDatabase();

                Cursor cursor = database.rawQuery("SELECT IDNotes, HeadNotes, DateTime FROM Notes;", null);
            try {
                String tempHeadNotes;
                String tempDateTime;
                int IDNotes;

                cursor.moveToFirst();
                if (cursor.getCount() > 0) {
                    do {

                        IDNotes = cursor.getInt(cursor.getColumnIndex("IDNotes"));
                        tempHeadNotes = cursor.getString(cursor.getColumnIndex("HeadNotes"));
                        tempDateTime = cursor.getString(cursor.getColumnIndex("DateTime"));

                        if (tempHeadNotes != null) {

                            NotesID.add(IDNotes);
                            NotesList.add(tempHeadNotes);
                            DateTimeList.add(tempDateTime);
                        }
                    } while (cursor.moveToNext());
                }
            }
            finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }).start();
        //-----------------------------------------------------

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(NotesList, DateTimeList);

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemViewCacheSize(30);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,10);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        Button button = v.findViewById(R.id.buttonCreateNotes);

        button.setOnClickListener(v1 -> {
            Intent intentCreateNote = new Intent(getContext(), CreateNote.class);
            startActivity(intentCreateNote);
        });

        SearchView  searchView = ((MainMenu)getActivity()).findViewById(R.id.SearchTextViewAll);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
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
            Collections.swap(DateTimeList, fromPosition, toPosition);

            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                NotesList.remove(position);
                DateTimeList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);

                SQLiteDatabase databaseDelete = sqLiteHelperKotlin.getWritableDatabase();
                sqLiteHelperKotlin.deleteNote(databaseDelete, position + 1);
            }
        }
    };
}


