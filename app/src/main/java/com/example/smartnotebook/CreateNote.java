package com.example.smartnotebook;

import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class CreateNote extends AppCompatActivity {

    View editTextHead;
    View editTextBody;
    private Button NotesButtonMenu;
    private TextView lastModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        //setAdjustScreen();
        
         editTextHead = findViewById(R.id.editTextHeadText);
         editTextBody= findViewById(R.id.editTextNotes);
         lastModifiedDate = findViewById(R.id.lastModifiedDate);

         NotesButtonMenu = findViewById(R.id.NotesButtonMenu);
         //Кнопка открытия меню
        NotesButtonMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showPopup(v);
            }
        });

        //------------------------Последние изменения в тексте---------------------------//
        editTextBody.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Date dateBody = new Date();
                    lastModifiedDate.setText("Последние изменения:\n" + dateBody.toString());
                }
            }
        });

        editTextHead.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    Date dateHead = new Date();
                    lastModifiedDate.setText("Последние изменения:\n" + dateHead.toString());
                }
            }
        });
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.getMenuInflater().inflate(R.menu.notes_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ImportItem:

                        return true;

                    default:
                        return false;
                }

            }
        });
        popup.show();
    }


    /*private void setAdjustScreen() {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        lastModifiedDate = null;
    }
}