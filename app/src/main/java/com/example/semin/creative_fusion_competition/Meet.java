package com.example.semin.creative_fusion_competition;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class Meet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        ImageView sample = (ImageView) findViewById(R.id.banana_gif);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(sample);
        Glide.with(Meet.this).load(R.drawable.sample).into(gifImage);
    }
}
