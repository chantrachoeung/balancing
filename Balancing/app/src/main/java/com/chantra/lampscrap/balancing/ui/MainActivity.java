package com.chantra.lampscrap.balancing.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chantra.lampscrap.api.adapter.BindingRecyclerAdapter;
import com.chantra.lampscrap.api.binder.CompositeItemBinder;
import com.chantra.lampscrap.api.binder.ItemBinder;
import com.chantra.lampscrap.api.config_module.InitializeStaticData;
import com.chantra.lampscrap.api.handlers.ClickHandler;
import com.chantra.lampscrap.api.key.K;
import com.chantra.lampscrap.api.utils.CurrencyUtils;
import com.chantra.lampscrap.balancing.BR;
import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.binder.TranInBinder;
import com.chantra.lampscrap.balancing.binder.TranOutBinder;
import com.chantra.lampscrap.balancing.databinding.ActivityMainBinding;
import com.chantra.lampscrap.balancing.mapping.TType;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.CurrentcyRealm;
import com.chantra.lampscrap.balancing.respository.objects.SettingRealm;
import com.chantra.lampscrap.balancing.respository.objects.TransactionInRealm;
import com.chantra.lampscrap.balancing.respository.objects.TransactionOutRealm;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.respository.objects.UserRealm;
import com.chantra.lampscrap.balancing.utils.DateUtils;
import com.chantra.lampscrap.balancing.utils.SessionManager;
import com.chantra.lampscrap.balancing.viewmodel.SettingViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TransactionInViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TransactionOutViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TransactionViewModel;
import com.chantra.lampscrap.balancing.viewmodel.TransactionsViewModel;

import org.joda.time.Months;
import org.joda.time.MutableDateTime;
import org.joda.time.Weeks;
import org.joda.time.Years;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ADD_INCOME = 100;
    private final static int REQUEST_ADD_EXPENSE = 200;

    private ActivityMainBinding mBinding;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    private BottomSheetBehavior behavior;
    private TransactionsViewModel transactionsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(SessionManager.init(this).getAccessToken()))
            InitializeStaticData.init(this).load();
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);

        mDrawerLayout = mBinding.drawer;

        transactionsViewModel = new TransactionsViewModel();
        mBinding.setTrans(transactionsViewModel);
        mBinding.setView(this);

        behavior = BottomSheetBehavior.from(mBinding.bottomSheet);
//        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                // React to state change
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//                // React to dragging events
//            }
//        });

        mBinding.navGroupLeft.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (checkedId == mBinding.navRadio1.getId()) {
                    filterBy(K.FilterMode.DAY);
                } else if (checkedId == mBinding.navRadio2.getId()) {
                    filterBy(K.FilterMode.WEEK);
                } else if (checkedId == mBinding.navRadio3.getId()) {
                    filterBy(K.FilterMode.MONTH);
                } else if (checkedId == mBinding.navRadio4.getId()) {
                    filterBy(K.FilterMode.YEAR);
                }
            }
        });

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mBinding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if (drawerView.getId() == mBinding.navLeft.getId()) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                }
                mToggle.syncState();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });
        mToggle.syncState();

        mBinding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSigOut();
            }
        });
        initSetting();
        initTransaction();
    }

    private boolean diff(K.FilterMode mode, MutableDateTime date) {
        if (mode == K.FilterMode.YEAR) {
            int num = Years.yearsBetween(date, mCurrentDate).getYears();
            if (num > 0)
                return true;
        } else if (mode == K.FilterMode.MONTH) {
            int num = Months.monthsBetween(date, mCurrentDate).getMonths();
            if (num > 0)
                return true;
        } else if (mode == K.FilterMode.WEEK) {
            int num = Weeks.weeksBetween(date, mCurrentDate).getWeeks();
            if (num > 0)
                return true;
        } else {
            return true;
        }
        return false;
    }

    private Date getDate(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd");
        if (null == date) {
            return format.parse(format.format(new Date()));
        }
        return format.parse(date);
    }

    private MutableDateTime mCurrentDate;

    private void filterBy(K.FilterMode mode) {
        RealmResults<TransactionInRealm> inRealms = RealmHelper.init(this).doQuery(TransactionInRealm.class).findAll();
        RealmResults<TransactionOutRealm> outRealms = RealmHelper.init(this).doQuery(TransactionOutRealm.class).findAll();

        transactionsViewModel.clear();
        mCurrentDate = new MutableDateTime();
        try {
            mCurrentDate.setDate(getDate(null).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TransactionInRealm newInRealm;
        int newInValue = 0;
        MutableDateTime date = new MutableDateTime();
        for (TransactionInRealm inRealm : inRealms) {
            try {
                newInValue += inRealm.getTotalAmount();
                date.setDate(getDate(inRealm.getDateCreated()).getTime());
                if (diff(mode, date)) {
                    newInRealm = new TransactionInRealm();
                    newInRealm.setDateCreated(inRealm.getDateCreated());
                    newInRealm.setDescription(inRealm.getDescription());
                    newInRealm.setTransactionType(inRealm.getTransactionTypeId());
                    newInRealm.setTransactionCategory(inRealm.getTransactionCategory());
                    newInRealm.setCurrentcy(inRealm.getCurrentcy());
                    newInRealm.setTotalAmount(newInValue);
                    transactionsViewModel.add(new TransactionInViewModel(newInRealm));
                    mCurrentDate = date;
                    newInValue = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        TransactionOutRealm newOutRealm;
        int newOutValue = 0;
        for (TransactionOutRealm outRealm : outRealms) {
            try {
                date.setDate(getDate(outRealm.getDateCreated()).getTime());
                newOutValue += outRealm.getTotalAmount();
                if (diff(mode, date)) {
                    newOutRealm = new TransactionOutRealm();
                    newOutRealm.setDescritpion(outRealm.getDescritpion());
                    newOutRealm.setTransactionType(outRealm.getTransactionType());
                    newOutRealm.setTransactionCategory(outRealm.getTransactionCategory());
                    newOutRealm.setDateCreated(outRealm.getDateCreated());
                    newOutRealm.setCurrentcy(outRealm.getCurrentcy());
                    newOutRealm.setTotalAmount(newOutValue);
                    transactionsViewModel.add(new TransactionOutViewModel(newOutRealm));
                    mCurrentDate = date;
                    newOutValue = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBinding.recyclerTransaction.requestLayout();
    }

    public void doSigOut() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing out...");
        progressDialog.show();
        final String accessToken = SessionManager.init(getApplicationContext()).getAccessToken();
        Realm realm = RealmHelper.init(getApplicationContext()).getRealm();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm userRealm = RealmHelper.init(getApplicationContext()).doQuery(UserRealm.class).equalTo("accessToken", accessToken, Case.SENSITIVE).findFirst();
                if (null != userRealm)
                    userRealm.setAccessToken("");
                else {
                    SessionManager.init(getApplicationContext()).reset();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (null != progressDialog)
                    progressDialog.dismiss();
                SessionManager.init(getApplicationContext()).reset();
                goLogin();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (null != progressDialog)
                    progressDialog.dismiss();
            }
        });
    }

    private void goLogin() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateBalance();
    }

    private void initTransaction() {
        mBinding.contentDashboard.addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_INCOME, false, -1, "");
            }
        });

        mBinding.contentDashboard.addExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, -1, "");
            }
        });

        mBinding.contentDashboard.accomodation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 1, "Accommodation");
            }
        });
        mBinding.contentDashboard.food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 2, "Food & Beverage");
            }
        });
        mBinding.contentDashboard.healthCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 3, "Health Care");
            }
        });
        mBinding.contentDashboard.cellPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 4, "Cell Phone");
            }
        });
        mBinding.contentDashboard.transportation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 5, "Transportation");
            }
        });
        mBinding.contentDashboard.other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 6, "Ohter");
            }
        });
        mBinding.contentDashboard.sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true, 7, "Sport & Gym");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            TType tType = new TType();
            TransactionTypeRealm typeRealm = tType.get(data.getExtras());
            int price = data.getExtras().getInt("price");
            int tTypeId = data.getExtras().getInt("tType");

            String description = data.getExtras().getString("description");
            switch (requestCode) {
                case REQUEST_ADD_EXPENSE:
                    mBinding.contentDashboard.circleViewBalance.setCircleColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                    doTransaction(typeRealm, price, true, tTypeId, description);
                    break;
                case REQUEST_ADD_INCOME:
                    mBinding.contentDashboard.circleViewBalance.setCircleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    doTransaction(typeRealm, price, false, tTypeId, description);
                    break;
            }
        } else {
            Toast.makeText(this, "Canceled Request", Toast.LENGTH_SHORT).show();
        }

        mBinding.contentDashboard.circleViewBalance.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.contentDashboard.circleViewBalance.setCircleColor(Color.DKGRAY);
            }
        }, 1000);
    }

    private void launchTransactionActivity(int requestCode, boolean expense, int typeId, String typeName) {
        TransactionActivity.launch(this, requestCode, expense, typeId, typeName);
    }

    private void initSetting() {
        BindingRecyclerAdapter<SettingViewModel> recyclerAdapter = new BindingRecyclerAdapter<>(BR.setting, R.layout.item_setting);
        mBinding.recyclerNavRight.setAdapter(recyclerAdapter);
        mBinding.recyclerNavRight.setLayoutManager(new LinearLayoutManager(this));
        SettingRealm settingRealm = new SettingRealm();
        settingRealm.setName("Budget");
        recyclerAdapter.addItem(new SettingViewModel(settingRealm));
        recyclerAdapter.setClickHandler(new ClickHandler<SettingViewModel>() {
            @Override
            public void onClick(SettingViewModel item) {
                item.launch(MainActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_option:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private static AtomicInteger id = new AtomicInteger();

    private void doTransaction(TransactionTypeRealm typeRealm, int price, boolean expense, int ttype, String note) {
        if (!expense) {
            TransactionInRealm transaction = new TransactionInRealm();
            transaction.setId(id.getAndIncrement());
            transaction.setCurrentcy(new CurrentcyRealm());
            transaction.setTransactionType(ttype);
            transaction.setTotalAmount(price);

            transaction.setDateCreated(DateUtils.getCurrentDate());
            transaction.setTransactionCategory(typeRealm);
            transaction.setDescription(note);
            RealmHelper.init(this).addObject(transaction, transactionInRealmChangeListener);
        } else {
            TransactionOutRealm transaction = new TransactionOutRealm();

            transaction.setId(id.getAndIncrement());
            transaction.setTotalAmount(price);
            transaction.setDateCreated(DateUtils.getCurrentDate());
            transaction.setTransactionCategory(typeRealm);
            transaction.setDescritpion(note);
            transaction.setTransactionType(ttype);
            RealmHelper.init(this).addObject(transaction, transactionOutRealmChangeListener);
        }
    }

    private RealmChangeListener transactionInRealmChangeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(Realm realm) {
            updateBalance();
        }
    };

    private RealmChangeListener transactionOutRealmChangeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(Realm realm) {
            updateBalance();
        }
    };

    private void updateBalance() {
        try {
            RealmResults<TransactionInRealm> inRealms = RealmHelper.init(this).doQuery(TransactionInRealm.class).findAll();
            RealmResults<TransactionOutRealm> outRealms = RealmHelper.init(this).doQuery(TransactionOutRealm.class).findAll();

            double tExpense = 0;
            double tIncome = 0;

            transactionsViewModel.clear();
            for (TransactionInRealm tIn : inRealms) {
                tIncome += tIn.getTotalAmount();
            }

            for (TransactionOutRealm tOut : outRealms) {
                tExpense += tOut.getTotalAmount();
            }

            mBinding.contentDashboard.currentExpanse.setText(CurrencyUtils.currentFormat(tExpense));
            mBinding.contentDashboard.currentBalance.setText(CurrencyUtils.currentFormat(tIncome));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ItemBinder<TransactionViewModel> itemViewBinder() {
        return new CompositeItemBinder<>(
                new TranInBinder(BR.in, R.layout.item_transaction_in),
                new TranOutBinder(BR.out, R.layout.item_transaction_out)
        );
    }

    public ClickHandler<TransactionViewModel> clickHandler() {
        return new ClickHandler<TransactionViewModel>() {
            @Override
            public void onClick(TransactionViewModel item) {

            }
        };
    }
}
