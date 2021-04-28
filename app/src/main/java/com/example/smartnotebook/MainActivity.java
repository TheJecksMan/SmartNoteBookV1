package com.example.smartnotebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  {

    private ProgressBar mProgress;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)  findViewById(R.id.imageViewSplashScreen);
        mProgress = (ProgressBar) findViewById(R.id.progressBarLoad);
        TextView mTextViewVersion = (TextView) findViewById(R.id.textViewVersion);

        try {

            mTextViewVersion.setText(this.getPackageManager() .getPackageInfo(this.getPackageName(), 0).versionName);
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //-------------------------//
        new Thread(new Runnable() {
            public void run() {
                doWork();
                StartApp();
                finish();
            }
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
        Intent mainIntent = new Intent(this, MainActivityMainMenu.class);
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
        Runtime.getRuntime().gc();
    }
}