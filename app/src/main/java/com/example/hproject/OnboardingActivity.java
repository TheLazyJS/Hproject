package com.example.hproject;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hproject.compose.OnboardingAdapter;

public class OnboardingActivity  extends AppCompatActivity {
    private ViewPager2 viewPager;
    private OnboardingAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        viewPager = findViewById(R.id.onboardingViewPager);
        adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
