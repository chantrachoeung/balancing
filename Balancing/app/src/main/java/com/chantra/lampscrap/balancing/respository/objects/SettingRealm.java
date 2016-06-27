package com.chantra.lampscrap.balancing.respository.objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by phalla on 6/27/2016.
 */
@RealmClass
public class SettingRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private int monthlyBudget;
}
