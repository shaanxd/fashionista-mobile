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
import com.shahid.fashionista_mobile.databinding.FragmentAdminNavigationBinding;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import javax.inject.Inject;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;

public class AdminNavigationFragment extends RootFragment {
    @Inject
    @Nullable
    AuthenticationResponse auth;
    private FragmentAdminNavigationBinding binding;

    public AdminNavigationFragment() {
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
        binding = FragmentAdminNavigationBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = findNavController(activity, R.id.admin_nav);
        setupWithNavController(binding.adminBottomNav, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title;

            switch (destination.getId()) {
                case R.id.admin_home: {
                    title = "OUR PRODUCTS";
                    break;
                }
                case R.id.admin_categories: {
                    title = "CATEGORIES";
                    break;
                }
                default: {
                    title = "INQUIRIES";
                }
            }

            binding.setText(title);
        });

        binding.addCategoryButton.setOnClickListener(this::onAddCategoryClick);
        binding.addProductButton.setOnClickListener(this::onAddProductClick);

    }

    private void onAddProductClick(View view) {
        CustomNavigator.navigate(rootNavController, R.id.action_adminNavigationFragment_to_addProductFragment);
    }

    private void onAddCategoryClick(View view) {
        CustomNavigator.navigate(rootNavController, R.id.action_adminNavigationFragment_to_addCategoryFragment);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
