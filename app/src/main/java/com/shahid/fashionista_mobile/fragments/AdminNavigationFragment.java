package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.AdminNavigationPagerAdapter;
import com.shahid.fashionista_mobile.databinding.FragmentAdminNavigationBinding;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class AdminNavigationFragment extends RootFragment {
    @Inject
    @Nullable
    AuthenticationResponse auth;
    private FragmentAdminNavigationBinding binding;
    private List<String> tabText = Arrays.asList("Products", "Inquiries", "Categories", "Profile");
    private List<Integer> tabIcons = Arrays.asList(
            R.drawable.icon_store,
            R.drawable.icon_orders,
            R.drawable.icon_favourite,
            R.drawable.icon_favourite
    );
    private List<String> tabHeadings = Arrays.asList("OUR PRODUCTS", "INQUIRIES", "CATEGORIES", "YOUR PROFILE");

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

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

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        binding.addCategoryButton.setOnClickListener(this::onAddCategoryClick);
        binding.addProductButton.setOnClickListener(this::onAddProductClick);

        viewPager.setAdapter(new AdminNavigationPagerAdapter(this));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.setText(tabHeadings.get(position));
                binding.setIndex(position);
            }
        });

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            //  tab.setText(tabText.get(position));
            tab.setIcon(tabIcons.get(position));
        })).attach();
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
