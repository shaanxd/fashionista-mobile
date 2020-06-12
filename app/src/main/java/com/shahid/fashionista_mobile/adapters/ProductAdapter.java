package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.onItemClickListener;
import com.shahid.fashionista_mobile.databinding.ViewHolderProductBinding;
import com.shahid.fashionista_mobile.dto.response.ProductResponse;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<ProductResponse> products;
    private onItemClickListener callback;

    public ProductAdapter(List<ProductResponse> products, onItemClickListener callback) {
        this.products = products;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderProductBinding binding = ViewHolderProductBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ViewHolderProductBinding binding;
        private onItemClickListener callback;

        ViewHolder(@NonNull ViewHolderProductBinding binding, onItemClickListener callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;
        }

        void bind(ProductResponse product) {
            binding.setProduct(product);

            Glide.with(binding.getRoot())
                    .load(product.getThumbnail())
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.productImage);

            binding.productCard.setOnClickListener(v -> callback.onItemClick(product.getId()));
        }
    }
}
