package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.databinding.FragmentSignUpBinding;
import com.shahid.fashionista_mobile.dto.request.SignUpRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.services.AuthenticationService;
import com.shahid.fashionista_mobile.store.SessionStorage;

import java.util.Date;

import javax.inject.Inject;

import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class SignUpFragment extends RootFragment implements View.OnClickListener, ServiceCallback {
    private static final String TAG = "SignUpFragment";

    private FragmentSignUpBinding binding;
    @Inject
    AuthenticationService authService;
    @Inject
    SessionStorage sessionStorage;
    private EditText emailTxt, firstNameTxt, lastNameTxt, passwordTxt, confirmPasswordTxt;
    private Button signUpBtn, signInBtn;
    private AwesomeValidation validator;

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
        validator = new AwesomeValidation(UNDERLABEL);
        validator.setContext(activity);
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

        signUpBtn = binding.signUpBtn;
        signInBtn = binding.signInBtn;

        validator.addValidation(emailTxt, Patterns.EMAIL_ADDRESS, "Invalid Email Address");
        validator.addValidation(firstNameTxt, RegexTemplate.NOT_EMPTY, "Please Enter First Name");
        validator.addValidation(lastNameTxt, RegexTemplate.NOT_EMPTY, "Please Enter Last Name");
        validator.addValidation(passwordTxt, "[0-9a-zA-Z]{6,}", "Password should contain at least 6 characters");
        validator.addValidation(confirmPasswordTxt, passwordTxt, "Passwords do not match");

        signUpBtn.setOnClickListener(this);
        signInBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInBtn: {
                onSignInClick();
                break;
            }
            case R.id.signUpBtn: {
                onSignUpClick();
                break;
            }
        }
    }

    private void onSignInClick() {
    }

    private void onSignUpClick() {
        if (validator.validate()) {
            String email = emailTxt.getText().toString();
            String name = firstNameTxt.getText().toString() + " " + lastNameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            String confirmPassword = confirmPasswordTxt.getText().toString();
            SignUpRequest request = new SignUpRequest(email, name, password, confirmPassword);
            authService.signUpUser(request, this);
        }
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
            rootNavController.navigate(R.id.action_signUpFragment_to_navigationFragment);
        }
    }

    @Override
    public void onFailure(String mErrorMessage) {
        Log.e(TAG, mErrorMessage);
    }
}
