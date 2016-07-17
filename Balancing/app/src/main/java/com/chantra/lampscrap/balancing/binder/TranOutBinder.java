package com.chantra.lampscrap.balancing.binder;

import com.chantra.lampscrap.api.binder.ConditionalDataBinder;
import com.chantra.lampscrap.balancing.viewmodel.TransactionOutViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TransactionViewModel;

/**
 * Created by phearom on 7/10/16.
 */
public class TranOutBinder extends ConditionalDataBinder<TransactionViewModel> {
    public TranOutBinder(int bindingVariable, int layoutId) {
        super(bindingVariable, layoutId);
    }

    @Override
    public boolean canHandle(TransactionViewModel model) {
        return model instanceof TransactionOutViewModel;
    }
}
