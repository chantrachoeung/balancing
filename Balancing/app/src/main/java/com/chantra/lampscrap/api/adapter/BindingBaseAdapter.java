package com.chantra.lampscrap.api.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chantra.lampscrap.api.handlers.ClickHandler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by phearom on 7/3/16.
 */
public class BindingBaseAdapter<T> extends BaseAdapter implements View.OnClickListener {
    private final static int KEY_ITEM = -1111;
    private List<T> mItems;
    private int mResourceId;
    private int mVariableId;
    private ClickHandler<T> clickHandler;

    public BindingBaseAdapter(int variableId, int resourceId) {
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

    public void remove(T item) {
        if (mItems.size() > 0)
            mItems.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mItems != null ? mItems.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mResourceId, parent, false);
        binding.setVariable(mVariableId, getItem(position));
        binding.getRoot().setTag(KEY_ITEM, getItem(position));
        binding.getRoot().setOnClickListener(this);
        return binding.getRoot();
    }

    public void setClickHandler(ClickHandler<T> clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public void onClick(View v) {
        if (null != clickHandler) {
            T item = (T) v.getTag(KEY_ITEM);
            clickHandler.onClick(item);
        }
    }
}
