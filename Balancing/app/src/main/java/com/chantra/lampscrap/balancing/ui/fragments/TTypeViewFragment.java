package com.chantra.lampscrap.balancing.ui.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chantra.lampscrap.api.binder.CompositeItemBinder;
import com.chantra.lampscrap.api.binder.ItemBinder;
import com.chantra.lampscrap.api.handlers.ClickHandler;
import com.chantra.lampscrap.api.key.K;
import com.chantra.lampscrap.balancing.BR;
import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.binder.TTypeBinder;
import com.chantra.lampscrap.balancing.databinding.FragmentViewTtypeBinding;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.viewmodel.TTypeViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TTypesViewModel;

import io.realm.RealmResults;

/**
 * Created by phearom on 7/10/16.
 */
public class TTypeViewFragment extends Fragment {
    private FragmentViewTtypeBinding mBinding;
    private OnTypeFragmentListener<TTypeViewModel> onTypeFragmentListener;
    private TTypesViewModel tTypesViewModel;

    private K.TType mTType = K.TType.INCOME;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onTypeFragmentListener = (OnTypeFragmentListener<TTypeViewModel>) context;
        } catch (Exception e) {
            throw new RuntimeException(context.getClass().getSimpleName() + " don't implement OnTypeFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_ttype, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tTypesViewModel = new TTypesViewModel();
        mBinding.setView(this);
        mBinding.setTtypes(tTypesViewModel);
        initCategories();

        mBinding.addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTypeFragmentListener.onAction(OnTypeFragmentListener.ACTION_GO_ADD, null);
            }
        });
    }

    public void setTType(K.TType tType) {
        this.mTType = tType;
    }

    private void initCategories() {
        int typeId = 1;
        if (mTType == K.TType.EXPENSE) {
            typeId = 0;
        }

        //RealmResults<TransactionTypeRealm> typeRealms = RealmHelper.init(getContext()).doQuery(TransactionTypeRealm.class).findAll();

        RealmResults<TransactionTypeRealm> typeRealms = RealmHelper.init(getContext()).doQuery(TransactionTypeRealm.class).equalTo("type", typeId).findAll();
        for (TransactionTypeRealm type : typeRealms) {
            tTypesViewModel.add(new TTypeViewModel(type));
        }
    }

    public ItemBinder<TTypeViewModel> itemViewBinder() {
        return new CompositeItemBinder<>(
                new TTypeBinder(BR.ttype, R.layout.item_ttype)
        );
    }

    public ClickHandler<TTypeViewModel> clickHandler() {
        return new ClickHandler<TTypeViewModel>() {
            @Override
            public void onClick(TTypeViewModel item) {
                onTypeFragmentListener.onAction(OnTypeFragmentListener.ACTION_SELECTED, item);
            }
        };
    }
}
