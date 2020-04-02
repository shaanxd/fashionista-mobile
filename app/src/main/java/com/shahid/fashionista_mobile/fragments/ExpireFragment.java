package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.dto.response.AuthResponse;
import com.shahid.fashionista_mobile.store.AuthStore;

import javax.inject.Inject;


public abstract class ExpireFragment extends RootFragment {
    @Inject
    AuthStore authStore;

    @Nullable
    @Inject
    AuthResponse authObj;

    public ExpireFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (authObj != null) {
            authStore.setAuthObserver(this, authParam -> {
                if (authParam == null) {
                    rootNavController.navigate(R.id.action_navigationFragment_to_logoutFragment);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authStore.removeAuthObserver(this);
    }
}
