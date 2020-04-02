package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.databinding.FragmentLoginBinding;
import com.shahid.fashionista_mobile.dto.request.AuthenticationRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.services.AuthenticationService;
import com.shahid.fashionista_mobile.store.SessionStorage;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Response;

public class LoginFragment extends RootFragment implements View.OnClickListener, ServiceCallback {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding binding;
    @Inject
    AuthenticationService authService;
    @Inject
    SessionStorage sessionStorage;
    private EditText emailTxt;
    private EditText passwordTxt;

    public LoginFragment() {
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailTxt = binding.emailTxt;
        passwordTxt = binding.passwordTxt;
        binding.signInBtn.setOnClickListener(this);
        binding.signUpBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInBtn: {
                signInUser();
                break;
            }
            case R.id.signUpBtn: {
                signUpUser();
                break;
            }
        }
    }

    private void signInUser() {
        String emailString = emailTxt.getText().toString();
        String passwordString = passwordTxt.getText().toString();

        if (emailString.isEmpty() || passwordString.isEmpty()) {
            Toast.makeText(activity, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        AuthenticationRequest authenticationRequest = new AuthenticationRequest(emailString, passwordString);
        authService.signInUser(authenticationRequest, this);

    }

    private void signUpUser() {
        rootNavController.navigate(R.id.action_loginFragment_to_signUpFragment);
    }

    @Override
    public void onSuccess(Response mResponse) {
        AuthenticationResponse response = (AuthenticationResponse) mResponse.body();
        if (response != null) {
            long expirationInMilliSeconds = response.getExpirationInSeconds() * 1000;
            // Start Timer to Logout
            ((TimerCallback) activity).start(expirationInMilliSeconds);
            // Expiration Date
            Date expirationDate = new Date(new Date().getTime() + expirationInMilliSeconds);
            // Set Expiration Date and Set Authentication Obj
            response.setExpirationDate(expirationDate);
            sessionStorage.setSession(response);
            rootNavController.navigate(R.id.action_loginFragment_to_navigationFragment);
        } else {
            // Error
        }
    }

    @Override
    public void onFailure(String mErrorMessage) {
        Log.e(TAG, mErrorMessage);
    }
}
