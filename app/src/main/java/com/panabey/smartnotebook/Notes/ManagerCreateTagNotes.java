package com.panabey.smartnotebook.Notes;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.util.RecyclerAdapterTag;

import java.util.ArrayList;
import java.util.List;

public class ManagerCreateTagNotes {

    private final Context context;

    private List<String> ListTag;
    private List<String> ColorTag;

    private RecyclerView recyclerViewTag;
    private RecyclerAdapterTag recyclerAdapterTag;

    public ManagerCreateTagNotes(Context context, RecyclerView recyclerViewTag)
    {
        this.context = context;
        this.recyclerViewTag = recyclerViewTag;
    }

    public void ManagerRecyclerViewTag(){
        CreateTag();
    }

    public void onClickAddTag() {
        if (ListTag.isEmpty()) {
            CreateTag();
            recyclerAdapterTag.notifyDataSetChanged();
        }
        if (ListTag.size() <= 3) {
            ListTag.add("Другое");
            //ColorTag.add()
            recyclerAdapterTag.notifyItemInserted(ListTag.size() - 1);
            //recyclerAdapterList.notifyItemInserted(ColorTag.size() - 1);
        }
    }

    private void CreateTag(){
        ListTag = new ArrayList<>();
        ColorTag = new ArrayList<>();
        recyclerAdapterTag = new RecyclerAdapterTag(ListTag);

        recyclerViewTag.setAdapter(recyclerAdapterTag);

        recyclerViewTag.setNestedScrollingEnabled(false);
        //recyclerViewTag.setItemViewCacheSize(5);
        //recyclerViewTag.getRecycledViewPool().setMaxRecycledViews(0, 2);
    }
}
