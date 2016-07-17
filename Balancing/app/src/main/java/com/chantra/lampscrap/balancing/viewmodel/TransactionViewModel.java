package com.chantra.lampscrap.balancing.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.chantra.lampscrap.balancing.BR;

/**
 * Created by phearom on 7/17/16.
 */
public abstract class TransactionViewModel extends BaseObservable {
    private String transactionType;
    private boolean shown = false;

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
}
