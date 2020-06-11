package com.shahid.fashionista_mobile.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shahid.fashionista_mobile.callbacks.onFileChangeListener;
import com.shahid.fashionista_mobile.databinding.ViewHolderFileBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> implements onFileChangeListener {
    private List<File> files;
    private boolean isOne;

    public FileAdapter(boolean isOne) {
        this.files = new ArrayList<>();
        this.isOne = isOne;
    }

    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderFileBinding binding = ViewHolderFileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new FileAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.ViewHolder holder, int position) {
        holder.bind(files.get(position), this, position);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    @Override
    public void registerFile(File file) {
        if (isOne) {
            files.clear();
        }
        files.add(file);
        notifyDataSetChanged();
    }

    @Override
    public void unregisterFile(int position) {
        files.remove(position);
        notifyDataSetChanged();
    }

    public List<File> getFiles() {
        return files;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolderFileBinding binding;

        ViewHolder(@NonNull ViewHolderFileBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(File file, FileAdapter callback, int position) {
            binding.setName(file.getName());
            binding.removeButton.setOnClickListener(v -> callback.unregisterFile(position));
        }
    }
}
