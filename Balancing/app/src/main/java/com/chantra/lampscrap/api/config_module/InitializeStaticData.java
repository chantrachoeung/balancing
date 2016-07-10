package com.chantra.lampscrap.api.config_module;

import android.content.Context;

import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phearom on 7/10/16.
 */
public class InitializeStaticData {
    private final static String IS_FIRST_INIT = "first_init";
    private static InitializeStaticData instance;
    private Context context;
    private SessionManager sessionManager;

    public InitializeStaticData(Context context) {
        sessionManager = SessionManager.init(context);
        this.context = context;
    }

    public static InitializeStaticData init(Context context) {
        if (null == instance)
            instance = new InitializeStaticData(context);
        return instance;
    }

    public void load() {
        if (sessionManager.getUserData(IS_FIRST_INIT, false)) {
            return;
        }
        sessionManager.saveUserData(IS_FIRST_INIT, true);
        /*Initialize block*/
        initTransactionType();
    }

    private void initTransactionType() {
        List<TransactionTypeRealm> list = new ArrayList<>();
        TransactionTypeRealm transactionTypeRealm = new TransactionTypeRealm();
        transactionTypeRealm.setId(1);
        transactionTypeRealm.setIcon(R.drawable.ic_accormodation);
        transactionTypeRealm.setName("Accommodation");
        list.add(transactionTypeRealm);

        transactionTypeRealm = new TransactionTypeRealm();
        transactionTypeRealm.setId(2);
        transactionTypeRealm.setIcon(R.drawable.ic_food);
        transactionTypeRealm.setName("Food");
        list.add(transactionTypeRealm);

        transactionTypeRealm = new TransactionTypeRealm();
        transactionTypeRealm.setId(3);
        transactionTypeRealm.setIcon(R.drawable.ic_food_drink);
        transactionTypeRealm.setName("Drink");
        list.add(transactionTypeRealm);

        RealmHelper.init(context).addObject(list);
    }
}