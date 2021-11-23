package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panabey.smartnotebook.util.LoadPrefStartup;

/**
 * Класс для исполнения загрузочного окна
 * главнного Activity.
 */
public class MainActivity extends AppCompatActivity  {

    private ProgressBar mProgress;
    private ImageView mImageView;
    private TextView textViewVersionApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageViewSplashScreen);
        mProgress = findViewById(R.id.progressBarLoad);
        textViewVersionApp = (TextView)findViewById(R.id.textViewVersionApp);

        Context context = getApplicationContext();
        /*
          Получнение текущей версии сборки.
          Для его отборажения в окне загрузки.
         */
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            textViewVersionApp.setText(pInfo.versionName);
        }
        catch (Exception e) {
            e.printStackTrace();

        }

        //Получние и загрузка файла конфигуации приложения
        LoadPrefStartup loadPrefStartup = new LoadPrefStartup(MainActivity.this);
        loadPrefStartup.FirstStartupApp();
        loadPrefStartup.DarkThemeLoadPref();

        //-------------------------//
        new Thread(() -> {
            doWork();
            StartApp();
            finish();
        }).start();
    }

    private void doWork() {
        for (int progress = 0; progress < 100; progress += 5) {
            try {
                Thread.sleep(100);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void StartApp(){
        Intent mainIntent = new Intent(this, MainMenu.class);
        startActivity(mainIntent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mImageView.setImageDrawable(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}