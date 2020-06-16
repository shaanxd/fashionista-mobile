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
import com.shahid.fashionista_mobile.adapters.ProductAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentSearchBinding;
import com.shahid.fashionista_mobile.dto.request.ProductTagRequest;
import com.shahid.fashionista_mobile.dto.response.ProductListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class SearchFragment extends ExpireFragment implements ServiceCallback {

    @Inject
    ProductService service;

    ProductTagRequest request;
    String term;

    RecyclerView products;

    ProductAdapter adapter;

    int current = 0;
    int total = 0;
    int size = 0;

    FragmentSearchBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);

        Bundle bundle = getArguments();

        if (bundle == null) {
            return;
        }

        term = bundle.getString("SEARCH_TERM", "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.setLoading(true);
        binding.backButton.setOnClickListener(this::onBackClick);
        binding.setTerm(term);

        products = binding.products;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);

        getProductsByName(current);
    }

    private void onBackClick(View view) {
        CustomNavigator.goBack(rootNavController);
    }

    private void onPreviousClick(View view) {
        if (current != 0) {
            getProductsByName(current - 1);
        }
    }

    private void onNextClick(View view) {
        if (current != total) {
            getProductsByName(current + 1);
        }
    }

    private void getProductsByName(int page) {
        binding.setLoading(true);
        service.getProductsByName(term, page, this);
    }

    @Override
    public void onSuccess(Response mResponse) {
        ProductListResponse page = (ProductListResponse) mResponse.body();

        if (page == null) {
            return;
        }

        current = page.getCurrent();
        total = page.getTotal();
        size = page.getProducts().size();

        binding.setCurrent(current);
        binding.setTotal(total);
        binding.setSize(size);

        if (adapter == null) {
            adapter = new ProductAdapter(page.getProducts(), this::onItemClick);
        } else {
            adapter.setProducts(page.getProducts());
        }
        products.setAdapter(adapter);

        binding.setLoading(false);
    }

    private void onItemClick(String id) {
        if (auth != null && auth.getRole().equals("ADMIN")) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("PRODUCT_ID", id);
        rootNavController.navigate(R.id.productFragment, bundle);
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setError(mErrorMessage);
        binding.setLoading(false);
    }
}
