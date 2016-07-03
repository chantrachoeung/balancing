package com.chantra.lampscrap.balancing.respository.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by phalla on 6/26/2016.
 */
@RealmClass
public class TransactionTypeRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int type;
    private TransactionTypeRealm parentType;

    public int getId() {
        return id;
    }

    public TransactionTypeRealm getParentType() {
        return parentType;
    }

    public void setParentType(TransactionTypeRealm parentType) {
        this.parentType = parentType;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
