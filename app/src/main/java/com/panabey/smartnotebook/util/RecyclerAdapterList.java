package com.panabey.smartnotebook.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.CreateList;
import com.panabey.smartnotebook.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.ViewHolder> {

    List<String> List;
    Context context;

    public RecyclerAdapterList(List<String> List, Context context) {
        this.List = List;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater1 = LayoutInflater.from(parent.getContext());
        View view = layoutInflater1.inflate(R.layout.item_recyclerview_list, parent, false);
        RecyclerAdapterList.ViewHolder viewHolderList = new RecyclerAdapterList.ViewHolder(view);
        return viewHolderList;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.containerList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_load_animate));
        holder.textViewList.setText(List.get(position));
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //ImageView imageView;
        TextView textViewList;
        RelativeLayout containerList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // imageView = itemView.findViewById(R.id.imageView);
            textViewList = itemView.findViewById(R.id.textViewList);
            containerList = itemView.findViewById(R.id.ContainerList);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
 /*
            TextView recycler = view.findViewHolderForAdapterPosition
            int clickedPosition = getAdapterPosition();
            TextView text1 = recyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.text1);
            //Toast.makeText(view.getContext(), NotesList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();
  */
            view.getContext().startActivity(new Intent(view.getContext(), CreateList.class));
        }

    }
}
