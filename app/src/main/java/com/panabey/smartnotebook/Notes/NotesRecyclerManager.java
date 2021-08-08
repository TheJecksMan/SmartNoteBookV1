package com.panabey.smartnotebook.Notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotesRecyclerManager {

    private List<String> List;
    private RecyclerAdapterList recyclerAdapterList;
    private RecyclerView recyclerView;


    Context context;

    public NotesRecyclerManager(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    private void CreateObject(){
        List = new ArrayList<>();

        recyclerAdapterList = new RecyclerAdapterList(List, context);
        recyclerView.setAdapter(recyclerAdapterList);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,20);
    }

    private void ManagerTouch(){

        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(List, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    List.remove(position);

                    recyclerView.getAdapter().notifyItemRemoved(position);
                    // recyclerAdapterList.notifyDataSetChanged();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void ManagerCreateNote(){
        CreateObject();
        ManagerTouch();
    }

    public void TaskListOnClick(){
        List.add("");
       recyclerAdapterList.notifyDataSetChanged();
    }
}
