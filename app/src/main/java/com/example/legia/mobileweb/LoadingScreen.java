package com.example.legia.mobileweb;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class LoadingScreen extends AppCompatActivity {
    private static int timeout = 2000;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        ImageView imgLoading = findViewById(R.id.loading);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.animate);
        Picasso.get().load(R.drawable.logoapp).into(imgLoading);
        imgLoading.startAnimation(a);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(LoadingScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, timeout);

    }
}
