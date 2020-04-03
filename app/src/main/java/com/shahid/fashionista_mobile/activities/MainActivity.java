package com.shahid.fashionista_mobile.activities;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.TimerCallback;
import com.shahid.fashionista_mobile.store.SessionStorage;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TimerCallback, Runnable {
    @Inject
    SessionStorage sessionStorage;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((FashionApp) getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void start(long difference) {
        handler = new Handler();
        handler.postDelayed(this, difference);
    }

    @Override
    public void destroy() {
        if (handler != null) {
            handler.removeCallbacks(this);
        }
    }

    @Override
    public void run() {
        sessionStorage.setSession(null);
    }
}
