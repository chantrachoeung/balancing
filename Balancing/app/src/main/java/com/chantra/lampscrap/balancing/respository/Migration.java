package com.chantra.lampscrap.balancing.respository;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by phalla on 6/26/2016.
 */
public class Migration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        oldVersion = 0;
    }
}
