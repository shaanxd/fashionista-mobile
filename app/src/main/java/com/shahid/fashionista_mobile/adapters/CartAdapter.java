package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.ItemClickCallback;
import com.shahid.fashionista_mobile.databinding.CartItemViewHolderBinding;
import com.shahid.fashionista_mobile.dto.response.CartItemResponse;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartItemResponse> items;
    private ItemClickCallback callback;

    public CartAdapter(List<CartItemResponse> items, ItemClickCallback callback) {
        this.items = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemViewHolderBinding binding = CartItemViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, callback);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), position == items.size() - 1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CartItemViewHolderBinding binding;
        ItemClickCallback callback;

        ViewHolder(@NonNull CartItemViewHolderBinding binding, ItemClickCallback callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;
        }

        void bind(CartItemResponse item, boolean isLast) {
            binding.setCart(item);
            binding.setIsLast(isLast);

            Glide.with(binding.getRoot())
                    .load(item.getProduct().getThumbnail())
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.productImage);

            binding.deleteBtn.setOnClickListener(v -> {
                callback.onItemClick(item.getId());
            });
        }
    }
}
