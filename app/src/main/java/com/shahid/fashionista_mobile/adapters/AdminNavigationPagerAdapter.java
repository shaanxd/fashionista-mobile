package com.shahid.fashionista_mobile.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shahid.fashionista_mobile.fragments.CategoriesFragment;
import com.shahid.fashionista_mobile.fragments.HomeFragment;
import com.shahid.fashionista_mobile.fragments.InquiriesFragment;
import com.shahid.fashionista_mobile.fragments.ProfileFragment;

public class AdminNavigationPagerAdapter extends FragmentStateAdapter {
    public AdminNavigationPagerAdapter(@NonNull Fragment fragment) {
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
                fragment = new InquiriesFragment();
                break;
            }
            case 2: {
                fragment = new CategoriesFragment();
                break;
            }
            default: {
                fragment = new ProfileFragment();
                break;
            }
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
