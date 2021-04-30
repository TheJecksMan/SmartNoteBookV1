package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivityNotes extends AppCompatActivity implements View.OnClickListener {

    Button BtnActCreateText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

        BtnActCreateText = (Button) findViewById(R.id.buttonCreateText);
        BtnActCreateText.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonCreateText:
                Intent intent = new Intent(this, MainActivityCreateNotes.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}