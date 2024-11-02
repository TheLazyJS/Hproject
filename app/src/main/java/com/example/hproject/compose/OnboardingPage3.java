package com.example.hproject.compose;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hproject.MainActivity;
import com.example.hproject.R;

public class OnboardingPage3  extends Fragment {

    Button finalizeBtn;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page3, container, false);

        finalizeBtn = view.findViewById(R.id.finalizeOnboardingButton);
        finalizeBtn.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), MainActivity.class));
            requireActivity().finish();
        });

        return view;
    }
}