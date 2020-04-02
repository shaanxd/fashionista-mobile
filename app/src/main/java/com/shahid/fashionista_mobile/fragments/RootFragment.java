package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.shahid.fashionista_mobile.R;

public abstract class RootFragment extends Fragment {
    Activity activity;
    NavController rootNavController;

    public RootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        rootNavController = Navigation.findNavController(activity.findViewById(R.id.nav_host));
    }
}
