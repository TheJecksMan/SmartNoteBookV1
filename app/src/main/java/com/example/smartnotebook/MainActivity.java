package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnActCreateReminders;
    Button btnActEditNoteBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActCreateReminders = (Button) findViewById(R.id.buttonSelectReminders);
        btnActCreateReminders.setOnClickListener(this);

        btnActEditNoteBook = (Button) findViewById(R.id.buttonEditNoteBook);
        btnActEditNoteBook.setOnClickListener(this);
    }

    @Override
    //Переход на другой Activity
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSelectReminders:
                Intent intent1 = new Intent(this, MainActivityReminders.class);
                startActivity(intent1);
                break;

            case R.id.buttonEditNoteBook:
                Intent intent2 = new Intent(this, MainActivityNoteBook.class);
                startActivity(intent2);
                break  ;

            default:
                break;
        }
    }
}