package com.chantra.lampscrap.balancing.respository.objects;

import java.sql.Date;

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
    private Date dateOfBirth;
    private double remainBalance;

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
}
