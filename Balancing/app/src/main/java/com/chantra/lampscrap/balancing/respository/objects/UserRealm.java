package com.chantra.lampscrap.balancing.respository.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by phalla on 6/26/2016.
 */
@RealmClass
public class UserRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String Gender;
    private String dateOfBirth;
    private double remainBalance;
    private String accessToken;
    private String signInDate;
    private String signUpDate;

    private SettingRealm setting;
    private TransactionInRealm income;
    private TransactionOutRealm payment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getRemainBalance() {
        return remainBalance;
    }

    public void setRemainBalance(double remainBalance) {
        this.remainBalance = remainBalance;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSignInDate() {
        return signInDate;
    }

    public void setSignInDate(String signInDate) {
        this.signInDate = signInDate;
    }

    public SettingRealm getSetting() {
        return setting;
    }

    public void setSetting(SettingRealm setting) {
        this.setting = setting;
    }

    public TransactionInRealm getIncome() {
        return income;
    }

    public void setIncome(TransactionInRealm income) {
        this.income = income;
    }

    public TransactionOutRealm getPayment() {
        return payment;
    }

    public void setPayment(TransactionOutRealm payment) {
        this.payment = payment;
    }

    public String getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(String signUpDate) {
        this.signUpDate = signUpDate;
    }
}
