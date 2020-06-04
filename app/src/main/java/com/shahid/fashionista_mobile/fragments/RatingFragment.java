package com.shahid.fashionista_mobile.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.shahid.fashionista_mobile.FashionApp;
import com.shahid.fashionista_mobile.adapters.ReviewAdapter;
import com.shahid.fashionista_mobile.callbacks.ServiceCallback;
import com.shahid.fashionista_mobile.databinding.FragmentRatingBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.dto.response.ReviewListResponse;
import com.shahid.fashionista_mobile.services.ProductService;

import javax.inject.Inject;

import retrofit2.Response;

public class RatingFragment extends RootFragment implements ServiceCallback {

    FragmentRatingBinding binding;
    ProductResponse product;
    RecyclerView reviewsList;
    ReviewAdapter adapter;

    int current = 0;
    int total = 0;
    int size = 0;

    @Inject
    ProductService service;

    public RatingFragment(ProductResponse product) {
        this.product = product;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((FashionApp) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRatingBinding.inflate(LayoutInflater.from(inflater.getContext()), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        reviewsList = binding.reviewsList;

        //Set pagination on clicks
        binding.nextButton.setOnClickListener(this::onNextClick);
        binding.previousButton.setOnClickListener(this::onPreviousClick);

        //Set Review data
        binding.setProduct(product);
        setReviewsToLayout(product.getReviews());
    }

    private void setReviewsToLayout(ReviewListResponse page) {
        current = page.getCurrent();
        total = page.getTotal();
        size = page.getReviews().size();

        binding.setCurrent(current);
        binding.setTotal(total);
        binding.setSize(size);

        if (adapter == null) {
            adapter = new ReviewAdapter(page.getReviews());
            reviewsList.setAdapter(adapter);
        } else {
            adapter.setReviews(page.getReviews());
        }

        binding.setLoading(false);
    }

    private void onNextClick(View v) {
        if (current != total) {
            binding.setLoading(true);
            service.getProductReviews(product.getId(), current + 1, 3, this);
        }
    }

    private void onPreviousClick(View v) {
        if (current != 0) {
            binding.setLoading(true);
            service.getProductReviews(product.getId(), current - 1, 3, this);
        }
    }

    @Override
    public void onSuccess(Response mResponse) {
        ReviewListResponse response = (ReviewListResponse) mResponse.body();
        if (response != null) {
            setReviewsToLayout(response);
        }
    }

    @Override
    public void onFailure(String mErrorMessage) {
        binding.setLoading(false);
        DynamicToast.makeError(activity, mErrorMessage, Toast.LENGTH_SHORT).show();
    }
}