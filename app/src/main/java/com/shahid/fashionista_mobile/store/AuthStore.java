package com.shahid.fashionista_mobile.store;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.shahid.fashionista_mobile.dto.response.AuthResponse;

public class AuthStore {
    private MutableLiveData<AuthResponse> authentication = new MutableLiveData<>();

    public AuthStore() {
        //  authentication.setValue(new AuthResponse());
    }

    public AuthResponse getAuthentication() {
        return authentication.getValue();
    }

    public void setAuthentication(AuthResponse authResponse) {
        authentication.setValue(authResponse);
    }

    public void setAuthenticationObserver(LifecycleOwner lifecycleOwner, Observer<AuthResponse> observer) {
        authentication.observe(lifecycleOwner, observer);
    }

    public void removeAuthenticationObserver(LifecycleOwner lifecycleOwner) {
        authentication.removeObservers(lifecycleOwner);
    }

}
