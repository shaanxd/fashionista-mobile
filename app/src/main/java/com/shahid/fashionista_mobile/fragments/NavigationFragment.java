package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.databinding.FragmentNavigationBinding;

public class NavigationFragment extends RootFragment {
    private FragmentNavigationBinding binding;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(activity, R.id.home_nav_host);
        bottomNavigationView = binding.homeBottomNavigation;
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public void onPause() {
        super.onPause();
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
}
