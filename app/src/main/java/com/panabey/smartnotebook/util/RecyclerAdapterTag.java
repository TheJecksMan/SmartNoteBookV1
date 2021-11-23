package com.panabey.smartnotebook.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.R;

import java.util.List;

public class RecyclerAdapterTag extends RecyclerView.Adapter<RecyclerAdapterTag.ViewHolder>{
    final List<String> ListTag;
    //final List<String> TagColor;

    public RecyclerAdapterTag(List<String> ListTag){
        this.ListTag = ListTag;
        //this.TagColor = TagColor;
    }

    @NonNull
    @Override
    public RecyclerAdapterTag.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterList = LayoutInflater.from(parent.getContext());
        View view = layoutInflaterList.inflate(R.layout.item_tag, parent, false);
        return new RecyclerAdapterTag.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTag.ViewHolder holder, int position) {
        holder.editTextTagNotes.setText(ListTag.get(position));
    }

    @Override
    public int getItemCount() {
        return ListTag.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final RelativeLayout containerTag;
        final TextView editTextTagNotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerTag = itemView.findViewById(R.id.containerTag);
            editTextTagNotes = itemView.findViewById(R.id.textViewEditTagNotes);
        }
    }
}
