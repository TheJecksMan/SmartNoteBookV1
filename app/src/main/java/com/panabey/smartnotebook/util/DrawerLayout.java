package com.panabey.smartnotebook.util;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;


public class DrawerLayout extends androidx.drawerlayout.widget.DrawerLayout {
    public DrawerLayout(@NonNull Context context) {
        super(context);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {

        return super.drawChild(canvas, child, drawingTime);
    }
}

