package com.chantra.lampscrap.balancing.viewmodel;

import com.chantra.lampscrap.balancing.respository.objects.TransactionInRealm;

/**
 * Created by phearom on 7/17/16.
 */
public class TransactionInViewModel extends TransactionViewModel {
    private TransactionInRealm model;

    public TransactionInViewModel(TransactionInRealm transactionInRealm) {
        this.model = transactionInRealm;
    }

    public TransactionInRealm getModel() {
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
