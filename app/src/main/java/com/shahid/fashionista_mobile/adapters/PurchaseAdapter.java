package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.StringFormatter;
import com.shahid.fashionista_mobile.callbacks.ItemClickCallback;
import com.shahid.fashionista_mobile.databinding.ViewHolderPurchaseBinding;
import com.shahid.fashionista_mobile.dto.response.PurchaseResponse;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    private List<PurchaseResponse> purchases;
    private ItemClickCallback callback;

    public PurchaseAdapter(List<PurchaseResponse> purchases, ItemClickCallback callback) {
        this.purchases = purchases;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ViewHolderPurchaseBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(purchases.get(position), callback);
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderPurchaseBinding binding;

        ViewHolder(ViewHolderPurchaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PurchaseResponse purchase, ItemClickCallback callback) {
            binding.setPurchase(purchase);

            String[] formattedDate = StringFormatter.getDayOfWeek(purchase.getOrderedAt()).split("-");

            binding.setDay(formattedDate[2]);
            binding.setMonth(formattedDate[1]);
            binding.setYear(formattedDate[0]);

            binding.purchaseLayout.setOnClickListener(v -> {
                String orderDetails = new Gson().toJson(purchase);
                callback.onItemClick(orderDetails);
            });
        }
    }
}
