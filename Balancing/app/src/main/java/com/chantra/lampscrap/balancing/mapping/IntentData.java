package com.chantra.lampscrap.balancing.mapping;

import android.os.Bundle;

/**
 * Created by phearom on 7/10/16.
 */
public abstract class IntentData<T> {
    private Bundle bundle;

    protected Bundle getBundle() {
        return this.bundle;
    }

    protected void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public IntentData() {
        bundle = new Bundle();
    }

    public void add(String key, String val) {
        bundle.putString(key, val);
    }

    public void add(String key, int val) {
        bundle.putInt(key, val);
    }

    public void add(String key, boolean val) {
        bundle.putBoolean(key, val);
    }

    public void add(String key, double val) {
        bundle.putDouble(key, val);
    }

    public String get(String key, String def) {
        return bundle.getString(key, def);
    }

    public int get(String key, int def) {
        return bundle.getInt(key, def);
    }

    public boolean get(String key, boolean def) {
        return bundle.getBoolean(key, def);
    }

    public double get(String key, double def) {
        return bundle.getDouble(key, def);
    }

    public abstract void put(T item);

    public abstract T get(Bundle bundle);

    public abstract Bundle toData();
}
