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
    private int transactionTypeId;
    private double value;
    private String dateCreated;
    private int unitPrice;



    private double quantity;
    private double totalAmount;
    private CurrentcyRealm currentcy;
    private String descritpion;
    private TransactionTypeRealm transcationType;

    public TransactionTypeRealm getTranscationType() {
        return transcationType;
    }

    public void setTranscationType(TransactionTypeRealm transcationType) {
        this.transcationType = transcationType;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
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

    private TransactionTypeRealm transactionCategory;

    public int getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(int transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTransactionType() {
        return transactionTypeId;
    }

    public void setTransactionType(int transactionType) {
        this.transactionTypeId = transactionType;
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }
}