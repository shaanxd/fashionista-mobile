package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.shahid.fashionista_mobile.R;
import com.shahid.fashionista_mobile.callbacks.onItemClickListener;
import com.shahid.fashionista_mobile.databinding.ViewHolderCategoryBinding;
import com.shahid.fashionista_mobile.dto.response.TagResponse;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<TagResponse> items;
    private boolean isReverse;
    private onItemClickListener callback;

    public CategoryAdapter(List<TagResponse> items, boolean isReverse, onItemClickListener callback) {
        this.items = items;
        this.isReverse = isReverse;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderCategoryBinding binding = ViewHolderCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), position == items.size() - 1, isReverse, callback);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderCategoryBinding binding;

        ViewHolder(@NonNull ViewHolderCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(TagResponse item, boolean isLast, boolean isReverse, onItemClickListener callback) {
            binding.setTag(item);
            binding.setLast(isLast);
            binding.setReverse(isReverse);

            Glide.with(binding.getRoot())
                    .load(item.getImage())
                    .placeholder(R.drawable.placeholder_img)
                    .into(binding.image);

            binding.category.setOnClickListener(v -> {
                callback.onItemClick(new Gson().toJson(item));
            });
        }
    }
}
