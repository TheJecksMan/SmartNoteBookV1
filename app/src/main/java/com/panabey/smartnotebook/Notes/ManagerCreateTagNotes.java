package com.panabey.smartnotebook.Notes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.Database.SQLiteHelperKotlin;
import com.panabey.smartnotebook.util.RecyclerAdapterTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManagerCreateTagNotes {

    private final Context context;

    private List<String> ListTag;
    private List<String> ColorTag;

    private final RecyclerView recyclerViewTag;
    private RecyclerAdapterTag recyclerAdapterTag;

    public ManagerCreateTagNotes(Context context, RecyclerView recyclerViewTag)
    {
        this.context = context;
        this.recyclerViewTag = recyclerViewTag;
    }

    public void ManagerRecyclerViewTag(){
        CreateTag();
        onTouchTag();
    }

    public void onClickAddTag() {
        if (ListTag.isEmpty()) {
            CreateTag();
            recyclerAdapterTag.notifyDataSetChanged();
        }
        if (ListTag.size() <= 2) {
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
        recyclerViewTag.setItemViewCacheSize(5);
        //recyclerViewTag.getRecycledViewPool().setMaxRecycledViews(0, 2);
    }

    public List<String> getListTag(){
        return ListTag;
    }

    public void loadTagFromDatabase(int IDNotes){

        SQLiteHelperKotlin sqLiteHelperKotlin = new SQLiteHelperKotlin(context);
        SQLiteDatabase database = sqLiteHelperKotlin.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM Tag where IDNotes = " + IDNotes, null);
        cursor.moveToFirst();

        for(int i = 0; i<cursor.getCount(); i++){
            ListTag.add(cursor.getString(cursor.getColumnIndex("NameTag")));
            recyclerAdapterTag.notifyItemInserted(i);
            cursor.moveToNext();
        }
        cursor.close();
        // recyclerAdapterList.notifyDataSetChanged();

    }

    private void onTouchTag() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.DOWN) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                Collections.swap(ListTag, fromPosition, toPosition);

                recyclerAdapterTag.notifyItemMoved(fromPosition, toPosition);
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.DOWN) {
                    ListTag.remove(position);
                    recyclerAdapterTag.notifyItemRemoved(position);
                }
            }
        };
        ItemTouchHelper itemTouchHelperTag = new ItemTouchHelper(simpleCallback);
        itemTouchHelperTag.attachToRecyclerView(recyclerViewTag);
    }
}
