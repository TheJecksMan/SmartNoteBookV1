package com.panabey.smartnotebook.Notes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.CreateNote;
import com.panabey.smartnotebook.util.RecyclerAdapterList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagerCreateNotes {

    private Context context;

    List<String> ListTask;
    RecyclerAdapterList recyclerAdapterList;
    RecyclerView recyclerView;

    public ManagerCreateNotes(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    private void CreateNote(){
        ListTask = new ArrayList<>();
        recyclerAdapterList = new RecyclerAdapterList(ListTask, context);

        recyclerView.setAdapter(recyclerAdapterList);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemViewCacheSize(30);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(0,20);

    }

    private void onTouch(){
        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(ListTask, fromPosition, toPosition);
                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    ListTask.remove(position);

                    recyclerView.getAdapter().notifyItemRemoved(position);
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
        ListTask.add("");
        recyclerAdapterList.notifyItemInserted(ListTask.size());
    }
}
