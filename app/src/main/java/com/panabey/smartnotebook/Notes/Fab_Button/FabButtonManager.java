package com.panabey.smartnotebook.Notes.Fab_Button;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.panabey.smartnotebook.R;

public class FabButtonManager{

    private FloatingActionButton fab_main, fab1_mail, fab2_share;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;

    private TextView textview_mail, textview_share;

    private Boolean isOpen = false;

    private Context context;

    public FabButtonManager(FloatingActionButton fab_main, FloatingActionButton fab1_mail, FloatingActionButton fab2_share,
                            TextView textview_mail, TextView textview_share,
                            Context context) {

        this.fab_main = fab_main;
        this.fab1_mail = fab1_mail;
        this.fab2_share = fab2_share;

        this.textview_mail = textview_mail;
        this.textview_share = textview_share;

        this.context = context;
    }

    public void FabOnClicked(){
        fab_close = AnimationUtils.loadAnimation(context, R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(context, R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(context, R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    textview_mail.setVisibility(View.INVISIBLE);
                    textview_share.setVisibility(View.INVISIBLE);

                    fab2_share.startAnimation(fab_close);
                    fab1_mail.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);

                    fab2_share.setClickable(false);
                    fab1_mail.setClickable(false);

                    isOpen = false;
                }
                else {
                    textview_mail.setVisibility(View.VISIBLE);
                    textview_share.setVisibility(View.VISIBLE);

                    fab2_share.startAnimation(fab_open);
                    fab1_mail.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);

                    fab2_share.setClickable(true);
                    fab1_mail.setClickable(true);

                    isOpen = true;
                }
            }
        });
    }
}
