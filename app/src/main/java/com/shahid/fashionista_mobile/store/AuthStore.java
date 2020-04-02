package com.shahid.fashionista_mobile.store;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.shahid.fashionista_mobile.dto.response.AuthResponse;

import javax.inject.Inject;

public class AuthStore {
    private MutableLiveData<AuthResponse> auth = new MutableLiveData<>();
    private SharedPrefStore sharedPrefStore;

    @Inject
    public AuthStore(SharedPrefStore sharedPrefStore) {
        this.sharedPrefStore = sharedPrefStore;
        loadAuthentication();
    }

    public AuthResponse getAuth() {
        return auth.getValue();
    }

    public void setAuth(AuthResponse authObj) {
        auth.setValue(authObj);
        if (authObj == null) {
            sharedPrefStore.clearAuthSharedPreferences();
        } else {
            sharedPrefStore.setAuthSharedPreferences(authObj);
        }
    }

    public void setAuthObserver(LifecycleOwner lifecycleOwner, Observer<AuthResponse> observer) {
        auth.observe(lifecycleOwner, observer);
    }

    public void removeAuthObserver(LifecycleOwner lifecycleOwner) {
        auth.removeObservers(lifecycleOwner);
    }

    private void loadAuthentication() {
        auth.setValue(sharedPrefStore.getAuthSharedPreferences());
    }
}
