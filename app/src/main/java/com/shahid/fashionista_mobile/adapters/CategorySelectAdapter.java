package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.callbacks.onTagClickListener;
import com.shahid.fashionista_mobile.databinding.ViewHolderCategorySelectBinding;
import com.shahid.fashionista_mobile.dto.response.TagResponse;

import java.util.List;

public class CategorySelectAdapter extends RecyclerView.Adapter<CategorySelectAdapter.ViewHolder> implements onTagClickListener {
    private List<TagResponse> items;
    private TagResponse tag;
    private int position;

    public CategorySelectAdapter(List<TagResponse> items) {
        this.items = items;
        this.position = -1;
        this.tag = null;
    }

    @NonNull
    @Override
    public CategorySelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderCategorySelectBinding binding = ViewHolderCategorySelectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategorySelectAdapter.ViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySelectAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), this.position == position, position == items.size() - 1);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onTagClick(TagResponse tag, int position) {
        this.tag = tag;
        this.position = position;
        notifyDataSetChanged();
    }

    public TagResponse getTag() {
        return tag;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderCategorySelectBinding binding;
        onTagClickListener callback;

        ViewHolder(@NonNull ViewHolderCategorySelectBinding binding, onTagClickListener callback) {
            super(binding.getRoot());
            this.binding = binding;
            this.callback = callback;
        }

        void bind(TagResponse item, boolean checked, boolean last) {
            binding.setTag(item);
            binding.setLast(last);
            binding.setChecked(checked);

            binding.radioButton.setOnClickListener(v -> {
                callback.onTagClick(item, getAdapterPosition());
            });
        }
    }
}
