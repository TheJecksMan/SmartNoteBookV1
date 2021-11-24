package com.panabey.smartnotebook.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.Editable;
import android.widget.RelativeLayout;
import android.widget.EditText;

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
        return new ViewHolder(view,  new EditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTag.ViewHolder holder, int position) {
        holder.EditTextListener.updatePosition(position);
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
        final EditText editTextTagNotes;
        final RecyclerAdapterTag.EditTextListener EditTextListener;

        public ViewHolder(@NonNull View itemView, RecyclerAdapterTag.EditTextListener EditTextListener) {
            super(itemView);
            containerTag = itemView.findViewById(R.id.containerTag);
            editTextTagNotes = itemView.findViewById(R.id.textViewEditTagNotes);

            this.EditTextListener = EditTextListener;
            this.editTextTagNotes.addTextChangedListener(EditTextListener);
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
                ListTag.set(position, s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}

