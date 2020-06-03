package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.SizeButtonAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentProductBinding;
import com.shahid.fashionista_mobile.dto.request.CartRequest;
import com.shahid.fashionista_mobile.dto.response.AuthenticationResponse;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.services.CartService;
import com.shahid.fashionista_mobile.services.ProductService;

import java.util.Arrays;

import javax.inject.Inject;

import it.sephiroth.android.library.numberpicker.NumberPicker;
import retrofit2.Response;

public class ProductFragment extends RootFragment {

    @Inject
    ProductService productService;
    @Inject
    CartService cartService;
    @Nullable
    @Inject
    AuthenticationResponse auth;

    private FragmentProductBinding binding;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<String> error = new MutableLiveData<>(null);
    private MutableLiveData<ProductResponse> product = new MutableLiveData<>(null);
    private MutableLiveData<Boolean> cart = new MutableLiveData<>(false);

    private String productId;

    private SizeButtonAdapter adapter;
    private NumberPicker quantityPicker;

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
        Bundle args = getArguments();
        if (args != null) {
            productId = args.getString("PRODUCT_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ServiceCallback productRequestCallback = new ServiceCallback() {
        @Override
        public void onSuccess(Response mResponse) {
            product.setValue((ProductResponse) mResponse.body());
            loading.setValue(false);
        }

        @Override
        public void onFailure(String mErrorMessage) {
            error.setValue(mErrorMessage);
            loading.setValue(false);
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quantityPicker = binding.quantityPicker;

        binding.addToCartBtn.setOnClickListener(this::onAddToCartClick);
        binding.addInquiryButton.setOnClickListener(this::onAddInquiryClick);
        binding.addReviewButton.setOnClickListener(this::onAddReviewClick);

        loading.observe(getViewLifecycleOwner(), this::onLoadingStateChange);
        product.observe(getViewLifecycleOwner(), this::onProductStateChange);
        error.observe(getViewLifecycleOwner(), this::onErrorStateChange);
        cart.observe(getViewLifecycleOwner(), this::onCartStateChange);

        productService.getProduct(productId, productRequestCallback);
    }

    @Override
    public void onPause() {
        super.onPause();
        loading.setValue(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        productService.getProduct(productId, productRequestCallback);

    }

    private void onAddInquiryClick(View view) {
        if (auth == null) {
            rootNavController.navigate(R.id.action_productFragment_to_loginFragment);
        } else {
            String value = new Gson().toJson(product.getValue());

            Bundle bundle = new Bundle();
            bundle.putString("PRODUCT_DETAILS", value);

            rootNavController.navigate(R.id.action_productFragment_to_addInquiryFragment, bundle);
        }
    }

    private void onAddReviewClick(View view) {
        if (auth == null) {
            rootNavController.navigate(R.id.action_productFragment_to_loginFragment);
        } else {
            String value = new Gson().toJson(product.getValue());

            Bundle bundle = new Bundle();
            bundle.putString("PRODUCT_DETAILS", value);

            rootNavController.navigate(R.id.action_productFragment_to_addReviewFragment, bundle);
        }
    }

    private void onCartStateChange(Boolean value) {
        binding.setCart(value);
    }

    private void onAddToCartClick(View view) {
        if (auth != null) {
            int quantity = quantityPicker.getProgress();
            int stock = product.getValue().getStock();
            String size = adapter.getSelectedSize();

            if (quantity == 0) {
                DynamicToast.makeWarning(activity, "Quantity cannot be zero").show();
                return;
            } else if (quantity > stock) {
                DynamicToast.makeWarning(activity, "Only " + stock + " pieces available.").show();
                return;
            }
            if (size == null) {
                DynamicToast.makeWarning(activity, "Please select a size.").show();
                return;
            }
            loading.setValue(true);
            cart.setValue(true);
            CartRequest request = new CartRequest(productId, quantity, size);
            cartService.addToCart("Bearer " + auth.getToken(), request, new ServiceCallback() {
                @Override
                public void onSuccess(Response mResponse) {
                    loading.setValue(false);
                    cart.setValue(false);
                    DynamicToast.makeSuccess(activity, "Added to cart successfully!").show();
                }

                @Override
                public void onFailure(String mErrorMessage) {
                    loading.setValue(false);
                    cart.setValue(false);
                    DynamicToast.makeError(activity, mErrorMessage).show();
                }
            });
        } else {
            rootNavController.navigate(R.id.action_productFragment_to_loginFragment);
        }
    }

    private void onLoadingStateChange(Boolean value) {
        binding.setLoading(value);
    }

    private void onProductStateChange(ProductResponse updated) {
        binding.setProduct(updated);

        if (updated != null) {
            // Set image
            Glide.with(binding.getRoot())
                    .load(updated.getThumbnail())
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.thumbnailImage);

            if (adapter == null) {
                adapter = new SizeButtonAdapter(Arrays.asList("S", "M", "L", "XL"));
            }
            binding.sizeButtonList.setAdapter(adapter);
            setProductDetailsFragments(updated);
        }
    }

    private void setProductDetailsFragments(ProductResponse updated) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.ratingView, new RatingFragment(updated));
        transaction.replace(R.id.inquiryView, new InquiryFragment(updated));
        transaction.commit();
    }

    private void onErrorStateChange(String error) {
        binding.setError(error);
    }
}
