package com.shahid.fashionista_mobile.dependency;

import com.shahid.fashionista_mobile.fragments.AuthFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Fragment injection functions
    void inject(AuthFragment x);
}
