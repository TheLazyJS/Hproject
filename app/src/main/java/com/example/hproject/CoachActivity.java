package com.example.hproject;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hproject.MainActivity;
import com.example.hproject.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CoachActivity extends BottomSheetDialogFragment {

    private MainActivity callback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the view of the pop-up
        View view = inflater.inflate(R.layout.coach_menu, container, false);

        return view;
    }

    // 3 next function is to be use with callback between MainActivity and SettingActivity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private void updatePrefs() {
        if (callback != null) {
        }
    }
}    