package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ItemClickCallback;
import com.shahid.fashionista_mobile.databinding.ProductViewHolderBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;
import com.shahid.fashionista_mobile.utils.ProductUtils;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductResponse> products;
    private ItemClickCallback callback;

    public ProductAdapter(List<ProductResponse> products, ItemClickCallback callback) {
        this.products = products;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductViewHolderBinding binding = ProductViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ProductViewHolderBinding binding;
        private ItemClickCallback callback;

        ViewHolder(@NonNull ProductViewHolderBinding binding, ItemClickCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;
        }

        void bind(ProductResponse product) {
            binding.setProduct(product);

            Glide.with(binding.getRoot())
                    .load(ProductUtils.getProductImageURL(product.getThumbnail()))
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.productImage);

            binding.productCard.setOnClickListener(v -> callback.onItemClick(product.getId()));
        }
    }
}
