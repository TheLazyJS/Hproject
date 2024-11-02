package com.example.hproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hproject.compose.ImagePagerAdapter;

import java.util.Arrays;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    public Button returnBtn, timerBtn;
    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private long timeElapsed = 0;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);

        // Timer code
        timerBtn = findViewById(R.id.timerBtn);
        timerBtn.setOnClickListener(v -> {
                if (!isRunning) {
                    startTimer();
                } else {
                    stopTimer();
                }
        });

        // Exercice Images Pager
        viewPager = findViewById(R.id.exe_pic_ViewPager);

        List<Integer> imageList = Arrays.asList(
                R.drawable.e001_1, R.drawable.e001_2, R.drawable.e001_3);
        List<String> captions = Arrays.asList(
                "incline push up", "normal push up", "decline push up");

        if (viewPager != null) {
            ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageList, captions);
            viewPager.setAdapter(adapter);
        } else {
            Log.e("ExerciseActivity", "ViewPager2: " + viewPager);
        }

        // Return Button
        returnBtn = findViewById(R.id.return_button);
        returnBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void startTimer() {
        isRunning = true;
        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeElapsed++;
                int minutes = (int) (timeElapsed / 60);
                int secs = (int) (timeElapsed % 60);
                String time = String.format("%02d:%02d", minutes, secs);
                timerBtn.setText(time);
            }

            @Override
            public void onFinish() {
                // Not used, as we don't intend to finish the timer
            }
        };
        countDownTimer.start();
    }

    private void stopTimer() {
        isRunning = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}

