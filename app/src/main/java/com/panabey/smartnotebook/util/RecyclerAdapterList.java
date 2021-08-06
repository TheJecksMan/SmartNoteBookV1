package com.panabey.smartnotebook.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.R;

import java.util.List;

public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.ViewHolder> {

    List<String> List;
    final Context context;

    public RecyclerAdapterList(List<String> List, Context context) {
        this.List = List;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterList = LayoutInflater.from(parent.getContext());

        View view = layoutInflaterList.inflate(R.layout.item_list, parent, false);
        RecyclerAdapterList.ViewHolder viewHolderList = new RecyclerAdapterList.ViewHolder(view);
        return viewHolderList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.BodyList.setText(List.get(position));
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final CheckBox CheckBox;
        final TextView BodyList;
        final RelativeLayout containerList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CheckBox = itemView.findViewById(R.id.checkBoxList);
            BodyList = itemView.findViewById(R.id.BodyList);
            containerList = itemView.findViewById(R.id.containerList);
        }
    }

    private static class EditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
