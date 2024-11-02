package com.example.hproject.compose;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OnboardingAdapter extends FragmentStateAdapter {
    public OnboardingAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new OnboardingPage1();
            case 1:
                return new OnboardingPage2();
            case 2:
                return new OnboardingPage3();
            default:
                return new OnboardingPage1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
