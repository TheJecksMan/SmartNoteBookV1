package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class CreateList extends AppCompatActivity {

    private Toolbar toolbarCreateList;
    private LinearLayout linearLayoutList;
    private Button buttonAddList;
    private CheckBox checkBox;

    private int countID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);

        toolbarCreateList = findViewById(R.id.toolbarUpPanelList);

        linearLayoutList = findViewById(R.id.LinearLayoutList);
        buttonAddList = findViewById(R.id.buttonAddList);

        final String TagText = "text";

        //Создание списка
        buttonAddList.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                checkBox = new CheckBox(getApplicationContext());
                checkBox.setText(TagText);
                checkBox.setTextColor(R.color.black);

                checkBox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Длительное нажатие!", Toast.LENGTH_LONG);
                        toast.show();
                        return true;
                    }
                });

                checkBox.setId(countID);
                linearLayoutList.addView(checkBox);
                countID++;
            }
        });

        toolbarCreateList.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBackMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intentBackMenu);
                countID = 0;
            }
        });

        //Верхняя панель активности
        toolbarCreateList.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.UndoItem:
                        break;
                    case R.id.RedoItem:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentBackList = new Intent(this, MainMenu.class);
        startActivity(intentBackList);
        finish();
        countID = 0;
    }
}