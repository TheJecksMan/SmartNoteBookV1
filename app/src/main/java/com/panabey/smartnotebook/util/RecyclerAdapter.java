package com.panabey.smartnotebook.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.CreateNote;
import com.panabey.smartnotebook.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<String> NotesList;
    Context context;
    public RecyclerAdapter(List<String> NotesList, Context context) {

        this.NotesList = NotesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.imageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.load_animate));
        holder.container.setAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_load_animate));
        holder.rowCountTextView.setText(String.valueOf(position));
        holder.textView.setText(NotesList.get(position));
    }

    @Override
    public int getItemCount() {

        return NotesList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        ImageView imageView;
        TextView textView, rowCountTextView;
        RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            container = itemView.findViewById(R.id.container);

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
            view.getContext().startActivity(new Intent(view.getContext(), CreateNote.class));


        }

    }


}
