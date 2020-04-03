package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.databinding.FragmentSignUpBinding;

public class SignUpFragment extends RootFragment {
    private FragmentSignUpBinding binding;

    private EditText emailTxt;
    private EditText firstNameTxt;
    private EditText lastNameTxt;
    private EditText passwordTxt;
    private EditText confirmPasswordTxt;

    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailTxt = binding.emailTxt;
        firstNameTxt = binding.firstNameTxt;
        lastNameTxt = binding.lastNameTxt;
        passwordTxt = binding.passwordTxt;
        confirmPasswordTxt = binding.confirmPasswordTxt;
    }
}
