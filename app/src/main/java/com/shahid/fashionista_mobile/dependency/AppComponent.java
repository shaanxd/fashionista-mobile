package com.shahid.fashionista_mobile.dependency;

import com.shahid.fashionista_mobile.activities.MainActivity;
import com.shahid.fashionista_mobile.fragments.AddCategoryFragment;
import com.shahid.fashionista_mobile.fragments.AddInquiryFragment;
import com.shahid.fashionista_mobile.fragments.AddProductFragment;
import com.shahid.fashionista_mobile.fragments.AddReviewFragment;
import com.shahid.fashionista_mobile.fragments.AdminNavigationFragment;
import com.shahid.fashionista_mobile.fragments.CartFragment;
import com.shahid.fashionista_mobile.fragments.CategoriesFragment;
import com.shahid.fashionista_mobile.fragments.CategoryProductFragment;
import com.shahid.fashionista_mobile.fragments.CheckoutFragment;
import com.shahid.fashionista_mobile.fragments.FavouritesFragment;
import com.shahid.fashionista_mobile.fragments.HomeFragment;
import com.shahid.fashionista_mobile.fragments.InquiriesFragment;
import com.shahid.fashionista_mobile.fragments.InquiryFragment;
import com.shahid.fashionista_mobile.fragments.LoginFragment;
import com.shahid.fashionista_mobile.fragments.NavigationFragment;
import com.shahid.fashionista_mobile.fragments.OrdersFragment;
import com.shahid.fashionista_mobile.fragments.ProductFragment;
import com.shahid.fashionista_mobile.fragments.ProfileFragment;
import com.shahid.fashionista_mobile.fragments.RatingFragment;
import com.shahid.fashionista_mobile.fragments.SignUpFragment;
import com.shahid.fashionista_mobile.fragments.SingleInquiryFragment;
import com.shahid.fashionista_mobile.fragments.SplashFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    // Activity injection functions
    void inject(MainActivity a);

    // Fragment injection functions
    void inject(LoginFragment f);

    void inject(SplashFragment f);

    void inject(SignUpFragment f);

    void inject(CartFragment f);

    void inject(HomeFragment f);

    void inject(OrdersFragment f);

    void inject(ProductFragment f);

    void inject(CheckoutFragment f);

    void inject(RatingFragment f);

    void inject(InquiryFragment f);

    void inject(AddInquiryFragment f);

    void inject(AddReviewFragment f);

    void inject(FavouritesFragment f);

    void inject(NavigationFragment f);

    void inject(AdminNavigationFragment f);

    void inject(InquiriesFragment f);

    void inject(ProfileFragment f);

    void inject(CategoriesFragment f);

    void inject(AddCategoryFragment f);

    void inject(AddProductFragment f);

    void inject(SingleInquiryFragment f);

    void inject(CategoryProductFragment f);
}
