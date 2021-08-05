package com.panabey.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panabey.smartnotebook.util.LoadPrefStartup;


public class MainActivity extends AppCompatActivity  {

    private ProgressBar mProgress;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.imageViewSplashScreen);
        mProgress = findViewById(R.id.progressBarLoad);
        TextView mTextViewVersion = findViewById(R.id.textViewVersion);

        //получение текущей версии приложения
        try {
            mTextViewVersion.setText(this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //загрузка файла конфигурации
        LoadPrefStartup loadPrefStartup = new LoadPrefStartup(MainActivity.this, this);
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