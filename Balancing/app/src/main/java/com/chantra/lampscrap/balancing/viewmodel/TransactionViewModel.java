package com.chantra.lampscrap.balancing.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.chantra.lampscrap.balancing.BR;

/**
 * Created by phearom on 7/17/16.
 */
public abstract class TransactionViewModel<T> extends BaseObservable {
    private String transactionType;
    private boolean shown = false;
    private T model;

    public TransactionViewModel(T item) {
        this.model = item;
    }

    public T getModel() {
        return this.model;
    }

    @Bindable
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        notifyPropertyChanged(BR.transactionType);
    }

    @Bindable
    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
        notifyPropertyChanged(BR.shown);
    }

    public View.OnClickListener onClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShown())
                    setShown(false);
                else
                    setShown(true);
            }
        };
    }
}
