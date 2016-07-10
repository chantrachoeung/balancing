package com.chantra.lampscrap.balancing.mapping;

import android.os.Bundle;

import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;

/**
 * Created by phearom on 7/10/16.
 */
public class TType extends IntentData<TransactionTypeRealm> {
    public TType() {
        super();
    }

    @Override
    public void put(TransactionTypeRealm item) {
        add("id", item.getId());
        add("icon", item.getIcon());
        add("name", item.getName());
        add("type", item.getType());
    }

    @Override
    public TransactionTypeRealm get(Bundle bundle) {
        setBundle(bundle);
        TransactionTypeRealm type = new TransactionTypeRealm();
        type.setId(get("id", 0));
        type.setName(get("name", null));
        type.setIcon(get("icon", 0));
        type.setType(get("type", -1));
        return type;
    }

    @Override
    public Bundle toData() {
        return getBundle();
    }
}
