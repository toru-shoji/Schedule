package com.toro_studio.schedule.views;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.toro_studio.schedule.BR;

public abstract class CustomRecyclerViewAdapter extends
        RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomRecyclerViewHolder>{

    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new CustomRecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CustomRecyclerViewHolder holder, final int position) {
        Object object = getObjectForPosition(position);
        holder.bind(object);
    }

    @Override
    public int getItemViewType(final int position) {
        return getLayoutIdForPosition(position);
    }

    static class CustomRecyclerViewHolder extends RecyclerView.ViewHolder{

        private final ViewDataBinding dataBinding;

        CustomRecyclerViewHolder(final ViewDataBinding binding) {
            super(binding.getRoot());
            dataBinding = binding;
        }

        void bind(final Object object) {
            dataBinding.setVariable(BR.model, object);
            dataBinding.executePendingBindings();
        }
    }

    protected abstract Object getObjectForPosition(final int position);

    protected abstract int getLayoutIdForPosition(final int position);

}