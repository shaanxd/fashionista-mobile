package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.databinding.FragmentNavigationBinding;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.store.SessionStorage;

import javax.inject.Inject;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class NavigationFragment extends RootFragment {
    private FragmentNavigationBinding binding;

    @Inject
    @Nullable
    AuthenticationResponse auth;

    @Inject
    SessionStorage session;

    NavController userNavController;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userNavController = findNavController(activity, R.id.user_nav);
        setupWithNavController(binding.userBottomNav, userNavController);

        binding.setAuth(auth);

        userNavController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title;

            switch (destination.getId()) {
                case R.id.user_home: {
                    title = "OUR PRODUCTS";
                    break;
                }
                case R.id.user_cart: {
                    title = "YOUR CART";
                    break;
                }
                case R.id.user_favourites: {
                    title = "YOUR WISHLIST";
                    break;
                }
                case R.id.user_orders: {
                    title = "YOUR ORDERS";
                    break;
                }
                default: {
                    title = "CATEGORIES";
                }
            }

            binding.setText(title);
        });

        binding.loginButton.setOnClickListener(this::onLoginClick);
        binding.logoutButton.setOnClickListener(this::onLogoutClick);
    }

    private void onLogoutClick(View view) {
        session.setSession(null);
        ((TimerCallback) activity).destroy();
    }

    private void onLoginClick(View view) {
        CustomNavigator.navigate(rootNavController, R.id.loginFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (auth == null) {
            CustomNavigator.navigate(userNavController, R.id.user_home);
        }
    }
}
