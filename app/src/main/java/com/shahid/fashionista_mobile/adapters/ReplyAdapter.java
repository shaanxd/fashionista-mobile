package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.databinding.ViewHolderReplyBinding;
import com.shahid.fashionista_mobile.dto.response.ReplyResponse;

import java.util.List;

public class ReplyAdapter extends RecyclerView.Adapter<ReplyAdapter.ViewHolder> {
    private List<ReplyResponse> replies;

    public ReplyAdapter(List<ReplyResponse> replies) {
        this.replies = replies;
    }

    @NonNull
    @Override
    public ReplyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderReplyBinding binding = ViewHolderReplyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ReplyAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReplyAdapter.ViewHolder holder, int position) {
        holder.bind(replies.get(position), position == replies.size() - 1);
    }

    public void setReplies(List<ReplyResponse> replies) {
        this.replies = replies;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return replies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderReplyBinding binding;

        ViewHolder(ViewHolderReplyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ReplyResponse reply, boolean last) {
            binding.setReply(reply);
            binding.setLast(last);
        }
    }
}
