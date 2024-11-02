package com.example.hproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.compose.ui.platform.ComposeView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hproject.compose.CalendarComposeActivityKt;
import com.example.hproject.compose.ComposeViewManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AdView adView;
    private View mCoach;
    private View mCoachLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton sBtn, activityBtn;

    Button doneBtn, finalizeBtn;

    @SuppressLint({"MissingInflatedId", "WrongViewCast", "RtlHardcoded"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ads package,initialize -> request and load
        MobileAds.initialize(this, initializationStatus -> {});
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        ComposeView composeView = findViewById(R.id.calendar_view);

        // Set the content of the ComposeView
        ComposeViewManager.INSTANCE.setComposableContent(
                composeView,
                //()-> {CalendarComposeActivityKt.MainCalendar();} //to pass parameters
                CalendarComposeActivityKt::MainCalendar
        );

        // Open coaching menu, which is a bottom sheet dialog
        mCoach = findViewById(R.id.coachBtn);
        mCoachLayout = findViewById(R.id.coach_menu);
        mCoach.setOnClickListener(v -> {
            CoachActivity bottomSheetDialog = new CoachActivity();
            bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheetDialogFragment");
        });

        // Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sBtn = findViewById(R.id.settingButton);
        sBtn.setOnClickListener(v -> {
            drawerLayout.openDrawer(Gravity.LEFT);
        });

        //Profile Drawer Btn
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {

            if (item.getItemId() == R.id.nav_home) {
                drawerLayout.closeDrawers();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                Intent intent = new Intent(MainActivity.this, OnboardingActivity.class);
                startActivity(intent);
                drawerLayout.closeDrawers();
                return true;
            } else if (item.getItemId() == R.id.nav_coaching){
                Toast.makeText(this, "Coaching link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_food){
                Toast.makeText(this, "Nutritionist link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_merch){
                Toast.makeText(this, "Merch link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_contact){
                Toast.makeText(this, "Contact link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_shared){
                Toast.makeText(this, "Rating link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            } else if (item.getItemId() == R.id.nav_rate){
                Toast.makeText(this, "Rating link not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });

        // Start/Exercice button
        doneBtn = findViewById(R.id.doneButton);
        doneBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
            startActivity(intent);
            doneBtn.setText("Done >");
        });
        activityBtn = findViewById(R.id.activityButton);
        activityBtn.setOnClickListener(v -> {
            doneBtn.setText("Done >");
        });

    }

    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

}


