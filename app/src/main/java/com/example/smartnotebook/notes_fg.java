package com.example.smartnotebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class notes_fg extends Fragment {

    private View view;
    private LinearLayout linearLayout;

    SQLiteHelper sqLiteHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_notes_fg, null);

        sqLiteHelper = new SQLiteHelper(getContext());
        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();

        int CountNotes = 0;
        /*
        Cursor c = database.rawQuery("select count(*) from Notes", null);

        CountNotes =c.getCount();

        if (CountNotes != 0 ) {
            for (int i = 0; i >= CountNotes; i++){
                Button b = new Button(getContext());

                b.setText(Note_To_Notes.Head);
                b.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT)
                );

                linearLayout.addView(b);

            }
        }

         */
        Button button = (Button) v.findViewById(R.id.buttonCreateNotes);
        linearLayout = (LinearLayout) v.findViewById(R.id.ListNotes);

        /*
        if(Note_To_Notes.Head != null) {
            Button b = new Button(getContext());

            b.setText(Note_To_Notes.Head);
            b.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
            );

            Note_To_Notes.Head = null;

            linearLayout.addView(b);
        }

         */


        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intentCreateNote = new Intent(getContext(), CreateNote.class);
                startActivity(intentCreateNote);
            }
        });

        return v;
    }

}