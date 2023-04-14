package com.qbit.qbitgamech;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.qbit.qbitgamech.adapter.SplashScreenViewPagerAdapter;
import com.qbit.qbitgamech.homeui.home.Home;

public class SplashScreen extends AppCompatActivity {
    ViewPager2 splashViewPager;

    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splashViewPager = findViewById(R.id.splashViewPager);
        SplashScreenViewPagerAdapter splashScreenViewPagerAdapter = new SplashScreenViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        splashViewPager.setAdapter(splashScreenViewPagerAdapter);
        ImageView imageView = findViewById(R.id.splashlogo);
        mediaPlayer = MediaPlayer.create(this, R.raw.iplhorntrumpet);
        mediaPlayer.start();
        // Create a scale animation
        Animation scaleAnimation = new ScaleAnimation(
                0.0f, 1.0f, // Start and end scale X
                0.0f, 1.0f, // Start and end scale Y
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X
                Animation.RELATIVE_TO_SELF, 0.5f // Pivot Y
        );

// Set the animation duration
        scaleAnimation.setDuration(2500);

// Set the animation listener
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Remove the image view from the parent view
                imageView.animate().alpha(0.0f).setDuration(1000).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

// Start the animation
        imageView.startAnimation(scaleAnimation);
        LottieAnimationView lottieAnimationView = findViewById(R.id.splashLottie);
        lottieAnimationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animator) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                if (sessionManager.checkSession() && firebaseAuth.getCurrentUser()!=null){
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                }else {
                    splashViewPager.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onAnimationCancel(@NonNull Animator animator) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animator) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }
}