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
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.NavigationPagerAdapter;
import com.shahid.fashionista_mobile.databinding.FragmentNavigationBinding;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class NavigationFragment extends RootFragment {
    private FragmentNavigationBinding binding;

    @Inject
    @Nullable
    AuthenticationResponse auth;

    private List<String> tabText = Arrays.asList("Home", "Cart", "Wishlist", "Orders");
    private List<Integer> tabIcons = Arrays.asList(
            R.drawable.icon_store,
            R.drawable.icon_cart,
            R.drawable.icon_favourite,
            R.drawable.icon_orders
    );

    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    public NavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
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

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        viewPager.setAdapter(new NavigationPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            tab.setText(tabText.get(position));
            tab.setIcon(tabIcons.get(position));
        })).attach();
    }

    @Override
    public void onPause() {
        super.onPause();
        // TODO
        if (auth == null) {
            viewPager.setCurrentItem(0);
        }
    }
}
