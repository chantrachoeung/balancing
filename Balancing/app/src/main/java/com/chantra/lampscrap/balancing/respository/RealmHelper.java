package com.chantra.lampscrap.balancing.respository;

import android.content.Context;

import java.util.Collection;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;

/**
 * Created by phalla on 6/26/2016.
 */
public class RealmHelper {
    private static RealmHelper instance;
    private Context mContext;
    private RealmConfiguration mConfig;

    private RealmHelper(Context context) {
        this.mContext = context;
    }

    public static RealmHelper init(Context context) {
        if (null == instance)
            instance = new RealmHelper(context);
        return instance;
    }

    private RealmConfiguration getConfig() {
        if (null == mConfig)
            mConfig = new RealmConfiguration.Builder(mContext)
                    .name("balancing.realm")
                    .schemaVersion(2)
                    .migration(new Migration())
                    .build();
        return mConfig;
    }

    public Realm getRealm() {
        return Realm.getInstance(getConfig());
    }

    public <T extends RealmObject> void addObject(T object) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.setAutoRefresh(true);
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public <T extends RealmObject> void addObject(T object, RealmChangeListener listener) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.setAutoRefresh(true);
        realm.addChangeListener(listener);
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

//    public <T extends RealmObject> void createUpdate(T object) {
//        Realm realm = getRealm();
//        realm.beginTransaction();
//        realm.setAutoRefresh(true);
//        realm.copyToRealmOrUpdate(object);
//        realm.commitTransaction();
//    }

    public <T extends RealmObject> void addObject(Collection<T> objects) {
        Realm realm = getRealm();
        realm.beginTransaction();
        realm.setAutoRefresh(true);
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();
    }

    public <T extends RealmObject> RealmQuery<T> doQuery(Class<T> clazz) {
        Realm realm = getRealm();
        return realm.where(clazz);
    }

    public <T extends RealmObject> int autoIncrement(Class<T> clazz, String field) {
        try {
            Realm realm = getRealm();
            return realm.where(clazz).max(field).intValue() + 1;
        } catch (Exception e) {
            return 1;
        }
    }
}
