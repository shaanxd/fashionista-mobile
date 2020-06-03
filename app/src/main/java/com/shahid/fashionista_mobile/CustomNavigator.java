package com.shahid.fashionista_mobile;

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
}
