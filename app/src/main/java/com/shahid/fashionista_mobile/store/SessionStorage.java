package com.shahid.fashionista_mobile.store;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;

import javax.inject.Inject;

public class SessionStorage {
    private MutableLiveData<AuthenticationResponse> session = new MutableLiveData<>();
    private SharedStorage sharedStorage;

    @Inject
    public SessionStorage(SharedStorage sharedStorage) {
        this.sharedStorage = sharedStorage;
        getFromSharedPrefs();
    }

    public AuthenticationResponse getSession() {
        return session.getValue();
    }

    public void setSession(AuthenticationResponse obj) {
        session.setValue(obj);
        if (obj == null) {
            sharedStorage.clearAuthSharedPreferences();
        } else {
            sharedStorage.setAuthSharedPreferences(obj);
        }
    }

    public void attachObserver(LifecycleOwner owner, Observer<AuthenticationResponse> observer) {
        session.observe(owner, observer);
    }

    public void detachObserver(LifecycleOwner owner) {
        session.removeObservers(owner);
    }

    private void getFromSharedPrefs() {
        session.setValue(sharedStorage.getAuthSharedPreferences());
    }
}
