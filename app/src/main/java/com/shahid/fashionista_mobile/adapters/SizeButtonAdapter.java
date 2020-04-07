package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.databinding.SizeButtonViewHolderBinding;

import java.util.List;

public class SizeButtonAdapter extends RecyclerView.Adapter<SizeButtonAdapter.ViewHolder> implements onSizeClickListener {
    private String size;
    private int position;
    private List<String> sizes;

    public SizeButtonAdapter(List<String> sizes) {
        this.sizes = sizes;
        this.position = -1;
        this.size = null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SizeButtonViewHolderBinding binding = SizeButtonViewHolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(sizes.get(position), this.position == position, position == sizes.size() - 1);
    }

    @Override
    public int getItemCount() {
        return sizes.size();
    }

    public String getSelectedSize() {
        return size;
    }

    @Override
    public void onSizeClick(String size, int position) {
        this.position = position;
        this.size = size;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        SizeButtonViewHolderBinding binding;
        onSizeClickListener listener;

        ViewHolder(@NonNull SizeButtonViewHolderBinding binding, onSizeClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        void bind(String size, boolean checked, boolean last) {
            binding.setIsLastItem(last);
            binding.radioButton.setText(size);
            binding.radioButton.setChecked(checked);

            binding.radioButton.setOnClickListener(v -> {
                listener.onSizeClick(size, getAdapterPosition());
            });
        }
    }
}
