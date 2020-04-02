package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.databinding.FragmentSplashBinding;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.store.SessionStorage;

import java.util.Date;

import javax.inject.Inject;

public class SplashFragment extends RootFragment {
    private FragmentSplashBinding binding;
    @Nullable
    @Inject
    AuthenticationResponse authState;
    @Inject
    SessionStorage sessionStorage;

    public SplashFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Check if existing authentication object is valid
        checkAuthenticationValidity();
    }

    private void checkAuthenticationValidity() {
        if (authState != null) {
            // Get current date and expiration date from existing authentication object
            Date currentDate = new Date();
            Date expirationDate = authState.getExpirationDate();

            // Get the difference between and logout or start logout timer based on a threshold value
            long difference = expirationDate.getTime() - currentDate.getTime();
            if (difference > 10000) {
                ((TimerCallback) activity).start(difference);
            } else {
                sessionStorage.setSession(null);
            }
        }
        rootNavController.navigate(R.id.action_splashFragment_to_home_nav_graph);
    }
}
