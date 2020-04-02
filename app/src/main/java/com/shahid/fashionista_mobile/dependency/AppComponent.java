package com.shahid.fashionista_mobile.dependency;

import com.shahid.fashionista_mobile.activities.MainActivity;
import com.shahid.fashionista_mobile.fragments.ExpireFragment;
import com.shahid.fashionista_mobile.fragments.LoginFragment;
import com.shahid.fashionista_mobile.fragments.SplashFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Activity injection functions
    void inject(MainActivity a);
    // Fragment injection functions
    void inject(ExpireFragment f);

    void inject(LoginFragment f);

    void inject(SplashFragment f);
}
