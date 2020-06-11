package com.shahid.fashionista_mobile;

import android.os.Bundle;

import androidx.navigation.NavController;

public class CustomNavigator {
    public static void navigate(NavController controller, int path) {
        try {
            controller.navigate(path);
        } catch (Exception ignored) {

        }
    }

    public static void goBack(NavController controller) {
        try {
            controller.navigateUp();
        } catch (Exception ignored) {

        }
    }

    public static void navigate(NavController controller, int path, Bundle bundle) {
        try {
            controller.navigate(path, bundle);
        } catch (Exception ignored) {

        }
    }
}
