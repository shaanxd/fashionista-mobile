package com.shahid.fashionista_mobile.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shahid.fashionista_mobile.fragments.CartFragment;
import com.shahid.fashionista_mobile.fragments.FavouritesFragment;
import com.shahid.fashionista_mobile.fragments.HomeFragment;
import com.shahid.fashionista_mobile.fragments.OrdersFragment;
import com.shahid.fashionista_mobile.fragments.SearchFragment;

public class NavigationPagerAdapter extends FragmentStateAdapter {
    public NavigationPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0: {
                fragment = new HomeFragment();
                break;
            }
            case 1: {
                fragment = new CartFragment();
                break;
            }
            case 2: {
                fragment = new FavouritesFragment();
                break;
            }
            case 3: {
                fragment = new OrdersFragment();
                break;
            }
            default: {
                fragment = new SearchFragment();
                break;
            }
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
