package com.shahid.fashionista_mobile.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shahid.fashionista_mobile.R;

public abstract class RootBaseFragment extends Fragment {
    protected Activity activity;
    protected NavController rootNavController;

    public RootBaseFragment() {
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
