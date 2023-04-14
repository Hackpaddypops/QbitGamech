package com.qbit.qbitgamech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.qbit.qbitgamech.homeui.home.Home;

public class Earn extends AppCompatActivity {
    LottieAnimationView liveStream;
    LottieAnimationView referEarn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earn);
        liveStream=findViewById(R.id.livestreamlottie);
        referEarn=findViewById(R.id.refer_and_earn_lottie);
        liveStream.setOnClickListener(view -> startActivity(new Intent(Earn.this, Home.class)));
        referEarn.setOnClickListener(view -> startActivity(new Intent(Earn.this, ReferAndEarn.class)));
    }
}