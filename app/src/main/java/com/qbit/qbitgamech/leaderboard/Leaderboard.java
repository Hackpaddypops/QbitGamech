package com.qbit.qbitgamech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class Leaderboard extends AppCompatActivity {

    TabLayout leaderboardTabLayout ;
    ViewPager2 leaderboardViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        leaderboardTabLayout = findViewById(R.id.leaderboardTabLayout);
        leaderboardViewPager= findViewById(R.id.leaderboardViewPager);
        LeaderboardViewPagerAdapter leaderboardViewPagerAdapter = new LeaderboardViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        leaderboardViewPager.setAdapter(leaderboardViewPagerAdapter);

        leaderboardTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                leaderboardViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        leaderboardViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                leaderboardTabLayout.selectTab(leaderboardTabLayout.getTabAt(position));
            }
        });
    }
}