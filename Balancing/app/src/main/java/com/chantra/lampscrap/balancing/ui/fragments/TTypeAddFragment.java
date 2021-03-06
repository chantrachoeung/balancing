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
import com.chantra.lampscrap.balancing.BR;
import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.binder.TTypeBinder;
import com.chantra.lampscrap.balancing.databinding.FragmentAddTtypeBinding;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.viewmodel.TTypeViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TTypesViewModel;

import io.realm.RealmResults;

/**
 * Created by phearom on 7/10/16.
 */
public class TTypeAddFragment extends Fragment {
    private FragmentAddTtypeBinding mBinding;
    private TTypesViewModel tTypesViewModel;
    private OnTypeFragmentListener<TTypeViewModel> onTypeFragmentListener;

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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_ttype, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tTypesViewModel = new TTypesViewModel();
        mBinding.setView(this);
        mBinding.setTtypes(tTypesViewModel);

        mBinding.tranAddWrappCategoryName.animate().alpha(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                mBinding.tranAddWrappCategoryName.setAlpha(1f);
                mBinding.tranAddWrappCategoryName.setVisibility(View.VISIBLE);
            }
        });

        initCategories();
    }

    private void initCategories() {
        RealmResults<TransactionTypeRealm> typeRealms = RealmHelper.init(getContext()).doQuery(TransactionTypeRealm.class).findAll();
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
                onTypeFragmentListener.onAction(OnTypeFragmentListener.ACTION_ADDED, item);
            }
        };
    }
}
