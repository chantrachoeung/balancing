package com.chantra.lampscrap.balancing.viewmodel;

import com.chantra.lampscrap.balancing.respository.objects.TransactionOutRealm;

/**
 * Created by phearom on 7/17/16.
 */
public class TransactionOutViewModel extends TransactionViewModel {
    private TransactionOutRealm model;

    public TransactionOutViewModel(TransactionOutRealm transactionOutRealm) {
        this.model = transactionOutRealm;
    }

    public TransactionOutRealm getModel() {
        return model;
    }

    public String getDateCreated() {
        return this.model.getDateCreated();
    }

    public int getUnitPrice() {
        return this.model.getUnitPrice();
    }

    public int getValue() {
        return this.getValue();
    }

    public int getDescription() {
        return this.getDescription();
    }
}
