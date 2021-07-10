package com.panabey.smartnotebook;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.panabey.smartnotebook.util.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class list_fg extends Fragment {

    private View view;
    private LinearLayout linearLayout;

    RecyclerView recyclerViewList;
    RecyclerAdapter recyclerAdapterList;
    List<String> List;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, null);

        List = new ArrayList<>();

        recyclerViewList = v.findViewById(R.id.RecyclerList);
        recyclerAdapterList = new RecyclerAdapter(List, getContext());

        recyclerViewList.setAdapter(recyclerAdapterList);
        recyclerViewList.setHasFixedSize(true);
        recyclerViewList.setItemViewCacheSize(30);


        Button createList = v.findViewById(R.id.buttonCreateList);


        createList.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intentCreateList = new Intent(getContext(), CreateList.class);
                startActivity(intentCreateList);
            }
        });

        return v;
    }
}