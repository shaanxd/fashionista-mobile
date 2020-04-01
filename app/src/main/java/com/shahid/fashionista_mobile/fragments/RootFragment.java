package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.shahid.fashionista_mobile.R;

public abstract class RootFragment extends Fragment {
    protected Activity activity;
    protected NavController rootNavController;

    public RootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
        View activityRootView = activity.findViewById(R.id.nav_host);
        rootNavController = Navigation.findNavController(activityRootView);
    }
}
