package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.CartAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentCartBinding;
import com.shahid.fashionista_mobile.dto.response.CartResponse;
import com.shahid.fashionista_mobile.services.CartService;

import javax.inject.Inject;

import retrofit2.Response;

public class CartFragment extends AuthFragment {

    @Inject
    CartService cartService;

    private FragmentCartBinding binding;

    private MutableLiveData<Boolean> loading = new MutableLiveData<>(true);
    private MutableLiveData<String> error = new MutableLiveData<>(null);
    private MutableLiveData<CartResponse> cart = new MutableLiveData<>(null);

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loading.observe(getViewLifecycleOwner(), this::onLoadingStateChange);
        cart.observe(getViewLifecycleOwner(), this::onCartStateChange);
        error.observe(getViewLifecycleOwner(), this::onErrorStateChange);

        binding.checkoutBtn.setOnClickListener(this::onCheckoutClick);
    }

    @Override
    public void onPause() {
        super.onPause();
        loading.setValue(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (auth != null) {
            loadCartFromApi();
        }
    }

    private void onCheckoutClick(View view) {
        Bundle bundle = new Bundle();
        String json = new Gson().toJson(cart.getValue());
        bundle.putString("CART_LIST", json);
        rootNavController.navigate(R.id.action_navigationFragment_to_checkoutFragment, bundle);
    }

    private void onErrorStateChange(String error) {
        binding.setError(error);
    }

    private void onCartStateChange(CartResponse cart) {
        binding.setCart(cart);

        if (cart != null) {
            CartAdapter adapter = new CartAdapter(cart.getItems(), this::onDeleteClick);
            binding.cartList.setAdapter(adapter);
        }
    }

    private void onLoadingStateChange(Boolean loading) {
        binding.setLoading(loading);
    }

    private void loadCartFromApi() {
        if (loading.getValue() != null && !loading.getValue()) {
            loading.setValue(true);
        }
        if (error.getValue() != null) {
            error.setValue(null);
        }
        if (auth == null) {
            return;
        }
        cartService.getCart("Bearer " + auth.getToken(), new ServiceCallback() {
            @Override
            public void onSuccess(Response mResponse) {
                cart.setValue((CartResponse) mResponse.body());
                loading.setValue(false);
            }

            @Override
            public void onFailure(String mErrorMessage) {
                error.setValue(mErrorMessage);
                loading.setValue(false);
            }
        });
    }

    private void onDeleteClick(String id) {
        loading.setValue(true);
        if (auth == null) {
            return;
        }
        cartService.deleteCart("Bearer " + auth.getToken(), id, new ServiceCallback() {
            @Override
            public void onSuccess(Response mResponse) {
                cart.setValue((CartResponse) mResponse.body());
                loading.setValue(false);
            }

            @Override
            public void onFailure(String mErrorMessage) {
                error.setValue(mErrorMessage);
                loading.setValue(false);
            }
        });
    }
}
