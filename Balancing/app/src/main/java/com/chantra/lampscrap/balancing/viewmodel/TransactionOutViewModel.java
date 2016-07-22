package com.chantra.lampscrap.balancing.viewmodel;

import com.chantra.lampscrap.balancing.respository.objects.TransactionOutRealm;

/**
 * Created by phearom on 7/17/16.
 */
public class TransactionOutViewModel extends TransactionViewModel<TransactionOutRealm> {

    public TransactionOutViewModel(TransactionOutRealm transactionOutRealm) {
        super(transactionOutRealm);
        setTransactionType("T Out");
    }

    public String getDateCreated() {
        return getModel().getDateCreated();
    }

    public int getUnitPrice() {
        return getModel().getUnitPrice();
    }

    public double getValue() {
        return getModel().getValue();
    }

    public String getDescription() {
        return getModel().getDescritpion();
    }
}
