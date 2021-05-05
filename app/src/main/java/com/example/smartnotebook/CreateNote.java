package com.example.smartnotebook;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Date;

public class CreateNote extends AppCompatActivity {

    View editTextHead;
    View editTextBody;

    private TextView lastModifiedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        setAdjustScreen();
        
         editTextHead = findViewById(R.id.editTextHeadText);
         editTextBody= findViewById(R.id.editTextNotes);
         lastModifiedDate = findViewById(R.id.lastModifiedDate);

       //------------------------Последние изменения-------------------------------//
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
        //---------------------Нижняя панель навигации----------------------------//
        /*downMenuEditText = findViewById(R.id.bottomNavigationViewEditText);
        downMenuEditText.setOnNavigationItemSelectedListener(navEditTextListener);

         */
    }

    /*
    private BottomNavigationView.OnNavigationItemSelectedListener navEditTextListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                default:
                    break;
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentPager, selectedFragment)
                    .commit();
            return true;
        }
    };

     */

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