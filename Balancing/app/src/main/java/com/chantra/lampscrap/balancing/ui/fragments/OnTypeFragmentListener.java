package com.chantra.lampscrap.balancing.ui.fragments;

public interface OnTypeFragmentListener<T> {
    int ACTION_ADDED = 1;
    int ACTION_SELECTED = 2;
    int ACTION_GO_ADD = 3;

    void onAction(int action, T item);
}