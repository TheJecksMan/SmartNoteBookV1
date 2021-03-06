package com.panabey.smartnotebook.Notes;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Класс для получения и обработки изображения,
 * для дальнейшего помещения его в базу данных
 */
public class FileManager extends AppCompatActivity {

    private final Context context;
    private final FloatingActionButton fab;

    public FileManager(Context context, FloatingActionButton fab){
        this.context = context;
        this.fab = fab;
    }

    public void OnClickFileManager(){
        fab.setOnClickListener(v -> ToOpenWith());
    }

    /**
     * Открытие файлового мендежера для получения path изображения
     */
    private void ToOpenWith(){

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT); //Открытие файлового менеджера
        intent.setType("image/*");
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        onActivityResult(RESULT_OK,100, intent);
    }

    /**
     * Получение path из Intent, для последующего хранения
     * в базе данных и обработки изображения
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
