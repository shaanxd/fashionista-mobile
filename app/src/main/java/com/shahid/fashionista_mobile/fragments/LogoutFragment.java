package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.databinding.FragmentLogoutBinding;

public class LogoutFragment extends RootFragment implements Runnable {
    private FragmentLogoutBinding binding;

    public LogoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(this, 3000);
    }

    @Override
    public void run() {
        rootNavController.navigate(R.id.action_logoutFragment_to_loginFragment);
    }
}
