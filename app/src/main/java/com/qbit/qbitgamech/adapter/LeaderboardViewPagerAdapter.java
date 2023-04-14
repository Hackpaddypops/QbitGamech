package com.qbit.qbitgamech.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qbit.qbitgamech.leaderboard.MonthlyLeaderboard;
import com.qbit.qbitgamech.leaderboard.TodayLeaderboard;
import com.qbit.qbitgamech.leaderboard.WeeklyLeaderboard;

public class LeaderboardViewPagerAdapter extends FragmentStateAdapter {
    public LeaderboardViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new TodayLeaderboard();
            case 1: return new WeeklyLeaderboard();
            case 2: return new MonthlyLeaderboard();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
