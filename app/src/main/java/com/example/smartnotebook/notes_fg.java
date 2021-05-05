package com.example.smartnotebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class notes_fg extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notes_fg, null);
        //return inflater.inflate(R.layout.fragment_notes_fg, container, false);
        Button button = (Button) v.findViewById(R.id.buttonCreateNotes);

        button.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intentCreateNote = new Intent(getContext(), CreateAndEditNote.class);
                startActivity(intentCreateNote);
            }
        });

        return v;
    }

}