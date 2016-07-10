package com.chantra.lampscrap.api.config_module;

import android.content.Context;

import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.utils.SessionManager;

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
        TransactionTypeRealm transactionTypeRealm = new TransactionTypeRealm();
        transactionTypeRealm.setId(1);
        transactionTypeRealm.setName("Accommodation");

        transactionTypeRealm = new TransactionTypeRealm();
        transactionTypeRealm.setId(2);
        transactionTypeRealm.setName("Food");

        RealmHelper.init(context).addObject(transactionTypeRealm);
    }
}
