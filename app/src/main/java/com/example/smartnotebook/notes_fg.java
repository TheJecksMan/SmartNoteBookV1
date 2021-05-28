package com.example.smartnotebook;

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
import android.widget.LinearLayout;

import com.example.smartnotebook.Database.SQLiteHelper;
import com.example.smartnotebook.util.RecyclerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class notes_fg extends Fragment {

    private View view;
    private LinearLayout linearLayout;
    SQLiteHelper sqLiteHelper;
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

        //---------------БД-----------------------------------
        sqLiteHelper = new SQLiteHelper(getContext());
        database = sqLiteHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM contactsNotes", null);

        String temp = null;
        cursor.moveToFirst();
        if(cursor!=null && cursor.getCount()>0) {
            do {
                temp = cursor.getString(cursor.getColumnIndex("HeadNotes"));
                if (temp != null) {
                    NotesList.add(temp);
                }
            } while (cursor.moveToNext());
        }

        //-----------------------------------------------------

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(NotesList, getContext());

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


        Button button = v.findViewById(R.id.buttonCreateNotes);
        //linearLayout = (LinearLayout) v.findViewById(R.id.ListNotes);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intentCreateNote = new Intent(getContext(), CreateNote.class);
                startActivity(intentCreateNote);
            }
        });

        return v;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            Collections.swap(NotesList, fromPosition, toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:
                    NotesList.remove(position);
                    recyclerView.getAdapter().notifyItemRemoved(position);

                    SQLiteDatabase databaseDelete = sqLiteHelper.getWritableDatabase();
                    sqLiteHelper.deleteNote(databaseDelete, position + 1);

                    //recyclerView.getAdapter().notifyDataSetChanged();
                    break;
            }
        }

    };
}


