package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Date;

public class CreateAndEditNote extends AppCompatActivity {

    View editTextHead;
    View editTextBody;
    TextView lastModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        setAdjustScreen();
        
         editTextHead = findViewById(R.id.editTextHeadText);
         editTextBody= findViewById(R.id.editTextNotes);
         lastModifiedDate = findViewById(R.id.lastModifiedDate);


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

    private void setAdjustScreen() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lastModifiedDate = null;
    }
}