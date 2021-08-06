package com.panabey.smartnotebook.util;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
        holder.CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    holder.BodyList.setPaintFlags(holder.BodyList.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    holder.BodyList.setPaintFlags(0);
                }
            }
        });
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
}
