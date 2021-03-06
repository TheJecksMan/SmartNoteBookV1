package com.panabey.smartnotebook.util;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.CreateNote;
import com.panabey.smartnotebook.R;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 Уважаемый разработчик:

 Как только ты прекратишь пытаться «оптимизировать» этот код
 и поймёшь, какую ошибку ты допустил взявшись за это дело,
 пожалуйста, увеличь номер на счётчике ниже
 для следующего разработчика:

 количество_часов_потрачено_впустую_здесь = 4
 **/
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    //final List<Integer> NotesID;
    final List<String> NotesList;
    final List<String> DateTimeList;
    final List<String> NotesListAll;

    public RecyclerAdapter(List<String> NotesList, List<String> DateTimeList) {
        this.NotesList = NotesList;
        this.DateTimeList = DateTimeList;
        //this.NotesID = NotesID;

        NotesListAll = new ArrayList<>();
        NotesListAll.addAll(NotesList);
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
        holder.textView.setText(NotesList.get(position));
        holder.textViewDate.setText(DateTimeList.get(position));
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
    public Filter getFilter() {
        return Searched_Filter;
    }

    protected Filter Searched_Filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0 ||constraint == "") {
                filteredList.addAll(NotesListAll);
            } else {
                for (String movie: NotesList) {
                    if (movie.toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(movie);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
                   protected void publishResults(CharSequence constraint, FilterResults results) {
                //DateTimeList.clear();

            NotesList.clear();
            NotesList.addAll((Collection<? extends String>) results.values);
            //notifyDataSetChanged();
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView textView, textViewDate;
        final RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textViewEditNotes);
            textViewDate = itemView.findViewById(R.id.textViewDateTime);
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
