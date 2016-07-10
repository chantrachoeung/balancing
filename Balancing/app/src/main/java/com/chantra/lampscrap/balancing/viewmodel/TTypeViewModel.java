package com.chantra.lampscrap.balancing.viewmodel;

import android.databinding.BaseObservable;

import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;

/**
 * Created by phearom on 7/3/16.
 */
public class TTypeViewModel extends BaseObservable {
    private TransactionTypeRealm model;

    public TTypeViewModel(TransactionTypeRealm ttypeRealm) {
        this.model = ttypeRealm;
    }

    public TransactionTypeRealm getModel() {
        return this.model;
    }

    public String getName() {
        return this.model.getName();
    }

    public Integer getIcon() {
        return this.model.getIcon();
    }
}
