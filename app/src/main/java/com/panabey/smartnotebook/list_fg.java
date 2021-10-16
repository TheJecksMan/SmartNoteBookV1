package com.panabey.smartnotebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class list_fg extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, null);

        Button createList = v.findViewById(R.id.buttonCreateList);

        createList.setOnClickListener(v1 -> {
            Intent intentCreateList = new Intent(getContext(), CreateList.class);
            startActivity(intentCreateList);
        });

        return v;
    }
}