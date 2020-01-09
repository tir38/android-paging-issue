package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MediaListAdapter extends PagedListAdapter<Media, MediaListAdapter.MediaViewHolder> {

    MediaListAdapter() {
        super(new MediaDiffCallback());
    }

    @NonNull
    @Override
    public MediaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view, parent, false);
        return new MediaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class MediaViewHolder extends RecyclerView.ViewHolder {
        private final TextView idView;

        MediaViewHolder(@NonNull View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.view_id);
        }

        void bind(Media item) {
            idView.setText(item.getId() + item.getContent());
        }
    }

    static class MediaDiffCallback extends DiffUtil.ItemCallback<Media> {
        @Override
        public boolean areItemsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Media oldItem, @NonNull Media newItem) {
            return oldItem.equals(newItem);
        }
    }
}
