package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
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
    private MutableLiveData<Boolean> loading = new MutableLiveData<>(false);
    private FragmentSignUpBinding binding;

    @Inject
    AuthenticationService authService;
    @Inject
    SessionStorage sessionStorage;

    private EditText emailTxt, firstNameTxt, lastNameTxt, passwordTxt, confirmPasswordTxt;
    private AwesomeValidation validator;
    private RelativeLayout hiddenLayout;
    private LinearLayout loadingLayout;

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

        hiddenLayout = binding.hiddenLayout;
        loadingLayout = binding.loadingLayout;

        validator.addValidation(emailTxt, Patterns.EMAIL_ADDRESS, getString(R.string.error_email_address));
        validator.addValidation(firstNameTxt, RegexTemplate.NOT_EMPTY, getString(R.string.error_first_name));
        validator.addValidation(lastNameTxt, RegexTemplate.NOT_EMPTY, getString(R.string.error_last_name));
        validator.addValidation(passwordTxt, "[0-9a-zA-Z]{6,}", getString(R.string.error_password));
        validator.addValidation(confirmPasswordTxt, passwordTxt, getString(R.string.error_confirm_password));

        binding.signUpBtn.setOnClickListener(this);
        binding.signInBtn.setOnClickListener(this);

        loading.observe(getViewLifecycleOwner(), this::onLoadingStateChange);
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
        rootNavController.navigate(R.id.action_signUpFragment_to_loginFragment);
    }

    private void onSignUpClick() {
        if (validator.validate()) {
            loading.setValue(true);

            String email = emailTxt.getText().toString();
            String name = firstNameTxt.getText().toString() + " " + lastNameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            String confirmPassword = confirmPasswordTxt.getText().toString();

            SignUpRequest request = new SignUpRequest(email, name, password, confirmPassword);
            authService.signUpUser(request, this);
        }
    }

    private void onLoadingStateChange(boolean value) {
        if (value) {
            hiddenLayout.setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        } else {
            loadingLayout.setVisibility(View.GONE);
            hiddenLayout.setVisibility(View.VISIBLE);
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
        loading.setValue(false);
        DynamicToast.makeError(activity, mErrorMessage).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        loading.removeObservers(this);
    }
}
