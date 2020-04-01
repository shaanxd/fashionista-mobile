package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.databinding.FragmentCartBinding;

public class CartFragment extends AuthenticatedBaseFragment {

    private FragmentCartBinding binding;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
