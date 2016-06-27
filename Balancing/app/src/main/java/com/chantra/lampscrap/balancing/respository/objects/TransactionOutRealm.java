package com.chantra.lampscrap.balancing.respository.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by phalla on 6/26/2016.
 */
@RealmClass
public class TransactionOutRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private int transactionType;
    private double value;
    private String dateCreated;
    private int unitPrice;
    private double quantity;
    private double totalAmount;
    private CurrentcyRealm currentcy;
    private TransactionTypeRealm transactionCategory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public TransactionTypeRealm getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionTypeRealm transactionCategory) {
        this.transactionCategory = transactionCategory;
    }
}