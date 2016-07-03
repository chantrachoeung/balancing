package com.chantra.lampscrap.api.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chantra.lampscrap.api.handlers.ClickHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by phearom on 7/3/16.
 */
public class BindingRecyclerAdapter<T> extends RecyclerView.Adapter<BindingRecyclerAdapter.RecyclerViewHolder> implements View.OnClickListener {
    private static final int KEY_ITEM = -1111;
    private List<T> mItems;
    private int mResourceId;
    private int mVariableId;

    private ClickHandler<T> clickHandler;

    private LayoutInflater mLayoutInflater;

    public BindingRecyclerAdapter(int variableId, int resourceId) {
        mItems = new ArrayList<>();
        mVariableId = variableId;
        mResourceId = resourceId;
    }

    public void addItem(T item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    public void setItems(Collection<T> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null == mLayoutInflater)
            mLayoutInflater = LayoutInflater.from(parent.getContext());

        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, mResourceId, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.binding.setVariable(mVariableId, mItems.get(position));
        holder.binding.getRoot().setTag(KEY_ITEM, mItems.get(position));
        holder.binding.getRoot().setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (null != clickHandler) {
            T item = (T) v.getTag(KEY_ITEM);
            clickHandler.onClick(item);
        }
    }

    public void setClickHandler(ClickHandler<T> clickHandler) {
        this.clickHandler = clickHandler;
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        public RecyclerViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
