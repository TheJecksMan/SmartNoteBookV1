package com.panabey.smartnotebook.Notes.Fab_Button;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Класс для получения и обработки изображения,
 * для дальнейшего помещения его в базу данных
 */
public class FileManager extends AppCompatActivity {

    private Context context;
    private FloatingActionButton fab;

    public FileManager(Context context, FloatingActionButton fab){
        this.context = context;
        this.fab = fab;
    }

    public void OnClickFileManager(){
        fab.setOnClickListener(v -> ToOpenWith());
    }

    private void ToOpenWith(){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT); //Открытие файлового менеджера
        intent.setType("image/*");
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        onActivityResult(RESULT_OK,100, intent);
    }

}
