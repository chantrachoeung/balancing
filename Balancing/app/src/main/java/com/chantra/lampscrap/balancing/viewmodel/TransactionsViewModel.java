package com.chantra.lampscrap.balancing.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.chantra.lampscrap.balancing.BR;

/**
 * Created by phearom on 7/17/16.
 */
public class TransactionsViewModel extends BaseObservable {
    @Bindable
    public ObservableList<TransactionViewModel> items;

    public TransactionsViewModel() {
        items = new ObservableArrayList<>();
    }

    public void add(TransactionViewModel item) {
        items.add(item);
        notifyPropertyChanged(BR.items);
    }

    public TransactionViewModel getItem(int pos) throws Exception {
        return items.get(pos);
    }

    public void clear() {
        items.clear();
        notifyPropertyChanged(BR.items);
    }

    public int getCount() {
        return items.size();
    }
}
