package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.databinding.ViewHolderInquiryBinding;
import com.shahid.fashionista_mobile.dto.response.InquiryResponse;

import java.util.List;

public class InquiryAdapter extends RecyclerView.Adapter<InquiryAdapter.ViewHolder> {
    private List<InquiryResponse> inquiries;

    public InquiryAdapter(List<InquiryResponse> inquiries) {
        this.inquiries = inquiries;
    }

    @NonNull
    @Override
    public InquiryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderInquiryBinding binding = ViewHolderInquiryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InquiryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InquiryAdapter.ViewHolder holder, int position) {
        holder.bind(inquiries.get(position));
    }

    public void setReviews(List<InquiryResponse> inquiries) {
        this.inquiries = inquiries;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return inquiries.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderInquiryBinding binding;

        ViewHolder(ViewHolderInquiryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(InquiryResponse inquiries) {
            binding.setInquiry(inquiries);
        }
    }
}
