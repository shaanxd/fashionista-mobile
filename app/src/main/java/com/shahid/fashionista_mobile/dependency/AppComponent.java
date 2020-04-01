package com.shahid.fashionista_mobile.dependency;

import com.shahid.fashionista_mobile.activities.MainActivity;
import com.shahid.fashionista_mobile.fragments.ExpireFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Activity injection functions
    void inject(MainActivity x);
    // Fragment injection functions
    void inject(ExpireFragment x);
}
