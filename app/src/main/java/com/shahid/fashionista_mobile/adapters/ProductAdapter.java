package com.shahid.fashionista_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.databinding.ProductViewHolderBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductResponse> products;
    private Context context;

    public ProductAdapter(List<ProductResponse> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolderBinding binding = ProductViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductResponse response = products.get(position);
        holder.binding.productName.setText(response.getName());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ProductViewHolderBinding binding;

        ViewHolder(@NonNull ProductViewHolderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
