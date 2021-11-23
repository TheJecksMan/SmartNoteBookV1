package com.panabey.smartnotebook.Notes.Fab_Button;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panabey.smartnotebook.R;

/**
 * Класс для исполнения логики кнопки для добавления следующих действий:
 * Открытия и сохранния изображения
 * Добавления подзадачи
 */
public class FabButtonManager{

    private final FloatingActionButton fab_main;
    private final FloatingActionButton fab1_task;
    private final FloatingActionButton fab2_textview_task;
    private final FloatingActionButton fab3_tag;

    private Animation fab_open;
    private Animation fab_close;
    private Animation fab_clock;
    private Animation fab_anticlick;

    private final TextView textview_task;
    private final TextView textview_attachments;
    private final TextView textview_tag;

    private Boolean isOpen = false;

    private final Context context;

    public FabButtonManager(FloatingActionButton fab_main, FloatingActionButton fab1_task,
                            FloatingActionButton fab2_textview_task, FloatingActionButton fab3_tag,
                            TextView textview_task, TextView textview_attachments, TextView textview_tag,
                            Context context) {

        this.fab_main = fab_main;
        this.fab1_task = fab1_task;
        this.fab2_textview_task = fab2_textview_task;
        this.fab3_tag = fab3_tag;

        this.textview_task = textview_task;
        this.textview_attachments = textview_attachments;
        this.textview_tag = textview_tag;

        this.context = context;
    }

    public void FabOnClicked(){
        fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock);
        fab_anticlick = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(view -> {

            if (isOpen) {
                textview_attachments.setVisibility(View.INVISIBLE);
                textview_tag.setVisibility(View.INVISIBLE);
                textview_task.setVisibility(View.INVISIBLE);

                fab3_tag.startAnimation(fab_close);
                fab2_textview_task.startAnimation(fab_close);
                fab1_task.startAnimation(fab_close);
                fab_main.startAnimation(fab_anticlick);

                fab3_tag.setClickable(false);
                fab2_textview_task.setClickable(false);
                fab1_task.setClickable(false);

                isOpen = false;
            }
            else {
                textview_tag.setVisibility(View.VISIBLE);
                textview_attachments.setVisibility(View.VISIBLE);
                textview_task.setVisibility(View.VISIBLE);

                fab3_tag.startAnimation(fab_open);
                fab2_textview_task.startAnimation(fab_open);
                fab1_task.startAnimation(fab_open);
                fab_main.startAnimation(fab_clock);

                fab3_tag.setClickable(true);
                fab2_textview_task.setClickable(true);
                fab1_task.setClickable(true);

                isOpen = true;
            }
        });
    }
}
