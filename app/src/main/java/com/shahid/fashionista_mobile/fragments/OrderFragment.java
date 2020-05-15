package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.adapters.CartAdapter;
import com.shahid.fashionista_mobile.databinding.FragmentOrderBinding;
import com.shahid.fashionista_mobile.dto.response.PurchaseResponse;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    private FragmentOrderBinding binding;
    private PurchaseResponse purchaseResponse;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            String orderDetailsString = args.getString("ORDER_DETAILS", null);
            if (orderDetailsString != null) {
                purchaseResponse = new Gson().fromJson(orderDetailsString, PurchaseResponse.class);
            } else {
                purchaseResponse = null;
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (purchaseResponse != null) {
            binding.setPurchase(purchaseResponse);
            binding.purchaseList.setAdapter(new CartAdapter(purchaseResponse.getPurchases(), null));
        }
    }
}
