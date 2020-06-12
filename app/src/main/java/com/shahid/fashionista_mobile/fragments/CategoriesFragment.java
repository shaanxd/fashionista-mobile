package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.CustomNavigator;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.adapters.CategoryAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentCategoriesBinding;
import com.shahid.fashionista_mobile.dto.response.AllTagsResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class CategoriesFragment extends ExpireFragment implements ServiceCallback {
    FragmentCategoriesBinding binding;

    RecyclerView brands, genders, types;

    @Inject
    ProductService service;

    public CategoriesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        brands = binding.brands;
        types = binding.types;
        genders = binding.genders;
    }

    @Override
    public void onSuccess(Response mResponse) {
        AllTagsResponse response = (AllTagsResponse) mResponse.body();

        if (response == null) {
            return;
        }

        brands.setAdapter(new CategoryAdapter(response.getBrands(), true, this::onItemClick));
        genders.setAdapter(new CategoryAdapter(response.getGenders(), false, this::onItemClick));
        types.setAdapter(new CategoryAdapter(response.getTypes(), true, this::onItemClick));

        binding.setLoading(false);

    }

    private void onItemClick(String tagJSON) {
        Bundle bundle = new Bundle();
        bundle.putString("TAG", tagJSON);
        CustomNavigator.navigate(rootNavController, R.id.categoryProductFragment, bundle);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setError(mErrorMessage);
        binding.setLoading(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.setLoading(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        service.getProductTags(this);
    }
}
