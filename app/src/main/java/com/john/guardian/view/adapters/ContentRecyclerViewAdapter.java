package com.john.guardian.view.adapters;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.john.guardian.R;
import com.john.guardian.databinding.ContentItemBinding;
import com.john.guardian.db.entity.GuardianContent;
import com.john.guardian.view.callbacks.ContentClickCallback;
import java.util.List;
import java.util.Objects;
import io.reactivex.annotations.Nullable;


public class ContentRecyclerViewAdapter extends RecyclerView.Adapter<ContentRecyclerViewAdapter.ContentViewHolder>
{
    private List<? extends GuardianContent> mContents;

    @Nullable
    private final ContentClickCallback callback;

    public ContentRecyclerViewAdapter(@Nullable ContentClickCallback callback)
    {
        this.callback = callback;
    }

    public void setContents(final List<? extends GuardianContent> contents)
    {
        if(this.mContents == null)
        {
            this.mContents = contents;
            notifyItemRangeInserted(0, contents.size());
        }
        else
        {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize()
                {
                    return mContents.size();
                }

                @Override
                public int getNewListSize()
                {
                    return contents.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
                {
                    return mContents.get(oldItemPosition).getId() == contents.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
                {
                    GuardianContent newSection = contents.get(newItemPosition);
                    GuardianContent oldSection = mContents.get(oldItemPosition);

                    return newSection.getId() == oldSection.getId()
                            && Objects.equals(newSection.getSectionName(), oldSection.getSectionName() )
                            && Objects.equals(newSection.getResourceName(), oldSection.getResourceName() )
                            && Objects.equals(newSection.getType(), oldSection.getType())
                            && Objects.equals(newSection.getWebTitle(), oldSection.getWebTitle() )
                            && Objects.equals(newSection.getWebPublicationDate(), oldSection.getWebPublicationDate() )
                            && Objects.equals(newSection.getWebUrl(), oldSection.getWebUrl()) ;
                }
            });

            mContents = contents;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        ContentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.content_item, parent, false);
        binding.setCallback(callback);
        return new ContentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position)
    {
        holder.binding.setContent(mContents.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mContents == null ? 0 : mContents.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder
    {
        final ContentItemBinding binding;
        public ContentViewHolder(ContentItemBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
