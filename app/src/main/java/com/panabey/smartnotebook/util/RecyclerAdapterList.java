package com.panabey.smartnotebook.util;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.panabey.smartnotebook.R;

import java.util.List;

/**
 Уважаемый разработчик:

 Как только ты прекратишь пытаться «оптимизировать» этот код
 и поймёшь, какую ошибку ты допустил взявшись за это дело,
 пожалуйста, увеличь номер на счётчике ниже
 для следующего разработчика:

 количество_часов_потрачено_впустую_здесь = 6
 **/

public class RecyclerAdapterList extends RecyclerView.Adapter<RecyclerAdapterList.ViewHolder> {

    final List<String> ListTask;
    final Context context;

    final List<Boolean> BooleanTask;

    public RecyclerAdapterList(List<String> ListTask, List<Boolean> BooleanTask, Context context) {
        this.ListTask = ListTask;
        this.BooleanTask = BooleanTask;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflaterList = LayoutInflater.from(parent.getContext());
        View view = layoutInflaterList.inflate(R.layout.item_list, parent, false);

        RecyclerAdapterList.ViewHolder viewHolderList = new RecyclerAdapterList.ViewHolder(view,  new EditTextListener());
        return viewHolderList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.BodyList.setText(ListTask.get(position));
        holder.EditTextListener.updatePosition(position);

        holder.CheckBox.setChecked(BooleanTask.get(position));
        holder.CheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                holder.BodyList.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                BooleanTask.set(position, true);
            }
            else{
                holder.BodyList.setPaintFlags(0);
                BooleanTask.set(position, false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ListTask.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        final CheckBox CheckBox;
        final EditText BodyList;
        final RelativeLayout containerList;
        final EditTextListener EditTextListener;

        public ViewHolder(@NonNull View itemView, EditTextListener EditTextListener) {
            super(itemView);

            CheckBox = itemView.findViewById(R.id.checkBoxList);
            BodyList = itemView.findViewById(R.id.BodyList);
            containerList = itemView.findViewById(R.id.containerList);

            this.EditTextListener = EditTextListener;
            this.BodyList.addTextChangedListener(EditTextListener);
        }
    }

    private class EditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {
            if (!s.toString().isEmpty()) {
                ListTask.set(position, s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}

