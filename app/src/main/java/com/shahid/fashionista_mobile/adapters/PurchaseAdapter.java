package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.shahid.fashionista_mobile.StringFormatter;
import com.shahid.fashionista_mobile.callbacks.onItemClickListener;
import com.shahid.fashionista_mobile.databinding.ViewHolderPurchaseBinding;
import com.shahid.fashionista_mobile.dto.response.PurchaseResponse;

import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    private List<PurchaseResponse> purchases;
    private onItemClickListener callback;

    public PurchaseAdapter(List<PurchaseResponse> purchases, onItemClickListener callback) {
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
        holder.bind(purchases.get(position), callback, position == purchases.size() - 1);
    }

    @Override
    public int getItemCount() {
        return purchases.size();
    }

    public void setPurchases(List<PurchaseResponse> purchases) {
        this.purchases = purchases;
        this.notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderPurchaseBinding binding;

        ViewHolder(ViewHolderPurchaseBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(PurchaseResponse purchase, onItemClickListener callback, boolean last) {
            binding.setPurchase(purchase);

            String[] formattedDate = StringFormatter.getDayOfWeek(purchase.getOrderedAt()).split("-");

            binding.setDay(formattedDate[2]);
            binding.setMonth(formattedDate[1]);
            binding.setYear(formattedDate[0]);
            binding.setLast(last);

            binding.purchaseLayout.setOnClickListener(v -> {
                String orderDetails = new Gson().toJson(purchase);
                callback.onItemClick(orderDetails);
            });
        }
    }
}
