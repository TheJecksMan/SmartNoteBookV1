package com.panabey.smartnotebook.Notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagerCreateNotes {

    private final Context context;

    List<String> ListTask;
    List<Boolean> BooleanTask;
    RecyclerAdapterList recyclerAdapterList;
    final RecyclerView recyclerView;

    public ManagerCreateNotes(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    private void CreateNote(){
        ListTask = new ArrayList<>();
        BooleanTask = new ArrayList<>();

        recyclerAdapterList = new RecyclerAdapterList(ListTask, BooleanTask, context);

        recyclerView.setAdapter(recyclerAdapterList);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,20);
    }

    /**
     * Навигация по RecyclerView.
     * Используется для перетаскивания и удаления заметок.
     */
    private void onTouch(){
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

    public void ManagerRecyclerView(){
        CreateNote();
        onTouch();
    }

    public void onClickAddItem(){
        if (ListTask.isEmpty()){
            CreateNote();
            recyclerAdapterList.notifyDataSetChanged();
        }
        ListTask.add("");
        BooleanTask.add(false);
        recyclerAdapterList.notifyItemInserted(ListTask.size()-1);
        recyclerAdapterList.notifyItemInserted(BooleanTask.size()-1);
    }
}
