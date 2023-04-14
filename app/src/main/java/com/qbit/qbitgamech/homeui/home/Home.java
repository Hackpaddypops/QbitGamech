package com.qbit.qbitgamech.homeui.home;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.qbit.qbitgamech.R;
import com.qbit.qbitgamech.databinding.ActivityHomeBinding;

public class Home extends AppCompatActivity {

    Toolbar toolbar;


    private ActivityHomeBinding binding;

    private final String YT_VIDEO_CHANNEL_ID="Youtube Video Id";
    private final String BALL_RESULT_CHANNEL_ID="Prediction Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createNotificationChannel();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_home);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        NotificationChannel ytNotificationChannel = new NotificationChannel(YT_VIDEO_CHANNEL_ID, YT_VIDEO_CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH);
        ytNotificationChannel.setDescription("Live Stream Begin/End notification.");
        // Register the channel with the system. You can't change the importance
        // or other notification behaviors after this.
        NotificationChannel ballResultNotificationChannel = new NotificationChannel(BALL_RESULT_CHANNEL_ID,BALL_RESULT_CHANNEL_ID,NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(ytNotificationChannel);
        notificationManager.createNotificationChannel(ballResultNotificationChannel);
    }

}