package com.chantra.lampscrap.balancing.binder;

import com.chantra.lampscrap.api.binder.ConditionalDataBinder;
import com.chantra.lampscrap.balancing.viewmodel.TTypeViewModel;

/**
 * Created by phearom on 7/10/16.
 */
public class TTypeBinder extends ConditionalDataBinder<TTypeViewModel> {
    public TTypeBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(TTypeViewModel model) {
        return true;
    }
}
