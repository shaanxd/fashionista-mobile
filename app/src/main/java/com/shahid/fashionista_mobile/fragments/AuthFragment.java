package com.shahid.fashionista_mobile.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.dto.response.AuthResponse;
import com.shahid.fashionista_mobile.store.AuthStore;

import javax.inject.Inject;

public abstract class AuthFragment extends RootFragment {
    @Inject
    AuthStore authStore;

    @Nullable
    @Inject
    AuthResponse authObj;

    public AuthFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (authObj == null) {
            rootNavController.navigate(R.id.action_navigationFragment_to_loginFragment);
        } else {
            authStore.setAuthenticationObserver(this, authParam -> {
                if (authParam == null) {
                    rootNavController.navigate(R.id.action_navigationFragment_to_loginFragment);
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        authStore.removeAuthenticationObserver(this);
    }
}
