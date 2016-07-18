package com.chantra.lampscrap.balancing.respository.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by phalla on 6/26/2016.
 */
@RealmClass
public class TransactionInRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private int transactionTypeId;
    private double value;
    private String dateCreated;
    private int unitPrice;
    private double quantity;
    private double totalAmount;
    private CurrentcyRealm currentcy;
    private TransactionTypeRealm transactionCategory;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionType(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
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

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public TransactionTypeRealm getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionTypeRealm transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public CurrentcyRealm getCurrentcy() {
        return currentcy;
    }

    public void setCurrentcy(CurrentcyRealm currentcy) {
        this.currentcy = currentcy;
    }
}