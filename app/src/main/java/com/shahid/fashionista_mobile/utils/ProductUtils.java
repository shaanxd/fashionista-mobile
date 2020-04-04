package com.shahid.fashionista_mobile.utils;

import static com.shahid.fashionista_mobile.constants.Constants.BASE_URL;

public class ProductUtils {
    public static String getProductImageURL(String resource) {
        return BASE_URL + "api/products/image/" + resource;
    }
}
