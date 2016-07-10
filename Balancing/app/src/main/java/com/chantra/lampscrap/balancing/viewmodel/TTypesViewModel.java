package com.chantra.lampscrap.balancing.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

/**
 * Created by phearom on 7/3/16.
 */
public class TTypesViewModel extends BaseObservable {
    @Bindable
    public ObservableList<TTypeViewModel> items;

    public TTypesViewModel() {
        items = new ObservableArrayList<>();
    }

    public void add(TTypeViewModel tTypeViewModel) {
        items.add(tTypeViewModel);
    }

    public TTypeViewModel getTTypeViewModel(int pos) throws Exception {
        return items.get(pos);
    }
}
