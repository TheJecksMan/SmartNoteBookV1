package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class EditText extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        Button editTextButtonMenu = findViewById(R.id.EditButtonMenu);

        editTextButtonMenu.setOnClickListener(v -> showEditPopup(v));

    }
    private void showEditPopup(View v) {
        PopupMenu editPopup = new PopupMenu(this, v);
        MenuInflater inflater = editPopup.getMenuInflater();
        editPopup.getMenuInflater().inflate(R.menu.text_edit_menu, editPopup.getMenu());

        editPopup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.ImportFileItem:

                    break;
                case R.id.SaveFileItem:
                    break;

                default:
                    return false;
            }
            return true;
        });
        editPopup.show();
    }

}
