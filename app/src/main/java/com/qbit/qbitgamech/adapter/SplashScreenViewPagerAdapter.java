package com.qbit.qbitgamech.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.qbit.qbitgamech.OnboardingScreen1;
import com.qbit.qbitgamech.OnboardingScreen2;
import com.qbit.qbitgamech.OnboardingScreen3;

public class SplashScreenViewPagerAdapter extends FragmentStateAdapter {
    public SplashScreenViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new OnboardingScreen1();
            case 1: return new OnboardingScreen2();
            case 2: return new OnboardingScreen3();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
