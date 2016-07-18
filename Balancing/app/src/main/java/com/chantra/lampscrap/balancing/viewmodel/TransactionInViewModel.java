package com.chantra.lampscrap.balancing.viewmodel;

import com.chantra.lampscrap.balancing.respository.objects.TransactionInRealm;

/**
 * Created by phearom on 7/17/16.
 */
public class TransactionInViewModel extends TransactionViewModel<TransactionInRealm> {

    public TransactionInViewModel(TransactionInRealm item) {
        super(item);
        //setTransactionType("T In");
    }

    public String getDateCreated() {
        return getModel().getDateCreated();
    }

    public double getTotalAmount(){return getModel().getTotalAmount();}

    public double getValue() {
        return getModel().getValue();
    }

    public String getType() {return getModel().getTransactionCategory().getName();}

    public String getDescription() {
        return getModel().getDescription();
    }
}
