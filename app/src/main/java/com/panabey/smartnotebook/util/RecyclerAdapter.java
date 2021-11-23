package com.panabey.smartnotebook.util;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.CreateNote;
import com.panabey.smartnotebook.R;


import java.util.List;
/**
 Уважаемый разработчик:

 Как только ты прекратишь пытаться «оптимизировать» этот код
 и поймёшь, какую ошибку ты допустил взявшись за это дело,
 пожалуйста, увеличь номер на счётчике ниже
 для следующего разработчика:

 количество_часов_потрачено_впустую_здесь = 4
 **/
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    final List<String> NotesList;

    public RecyclerAdapter(List<String> NotesList) {
        this.NotesList = NotesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rowCountTextView.setText(String.valueOf(position));
        holder.textView.setText(NotesList.get(position));
    }

    @Override
    public int getItemCount() {
        return NotesList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        super.onFailedToRecycleView(holder);
        return true;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textView, rowCountTextView;
        final RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewEditNotes);
            rowCountTextView = itemView.findViewById(R.id.rowCountTextView);
            container = itemView.findViewById(R.id.containerNotes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            /**
             *  Переход по нажатия для открытия заметок.
             *  Открытие происходим по id списка.
             *  P.S id списка должен соппадать с id в БД.
             */
            Intent intentOnClickRecyclerView =  new Intent(view.getContext(), CreateNote.class);
            intentOnClickRecyclerView.putExtra("BooleanClickRecyclerView", true);

            intentOnClickRecyclerView.putExtra("Id", getAdapterPosition() + 1);

            view.getContext().startActivity(intentOnClickRecyclerView);
        }
    }
}
