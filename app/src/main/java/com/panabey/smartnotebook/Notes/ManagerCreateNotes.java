package com.panabey.smartnotebook.Notes;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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
    List<Boolean> BooleanTask;
    RecyclerAdapterList recyclerAdapterList;
    RecyclerView recyclerView;

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

    private void onTouch(){
        final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                Collections.swap(ListTask, fromPosition, toPosition);
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

    public StringBuilder WriteAndUpdateTask(){
        StringBuilder stringBuilder = new StringBuilder();

        for (String text: ListTask){
            stringBuilder.append(text + " %`~ ");
        }

        return  stringBuilder;
    }

    public void ManagerRecyclerView(){
        CreateNote();
        onTouch();
    }

    public void onClickAddItem(){
        ListTask.add("");
        BooleanTask.add(false);
        recyclerAdapterList.notifyItemInserted(ListTask.size()-1);
        recyclerAdapterList.notifyItemInserted(BooleanTask.size()-1);

    }
}
