package com.chantra.lampscrap.balancing.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chantra.lampscrap.api.adapter.BindingRecyclerAdapter;
import com.chantra.lampscrap.api.handlers.ClickHandler;
import com.chantra.lampscrap.balancing.BR;
import com.chantra.lampscrap.balancing.R;
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

import java.text.NumberFormat;
import java.util.Locale;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {
    private final static int REQUEST_ADD_INCOME = 100;
    private final static int REQUEST_ADD_EXPENSE = 200;
    private ActivityMainBinding mBinding;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);

        mDrawerLayout = mBinding.drawer;
        mBinding.navGroupLeft.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
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
                    goLogin();
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

    private void initTransaction() {
        mBinding.contentDashboard.addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_INCOME, false);
            }
        });

        mBinding.contentDashboard.addExpend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTransactionActivity(REQUEST_ADD_EXPENSE, true);
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
            switch (requestCode) {
                case REQUEST_ADD_EXPENSE:
                    mBinding.contentDashboard.circleViewBalance.setCircleColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
                    doTransaction(typeRealm, price, true);
                    break;
                case REQUEST_ADD_INCOME:
                    mBinding.contentDashboard.circleViewBalance.setCircleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
                    doTransaction(typeRealm, price, false);
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

    private void launchTransactionActivity(int requestCode, boolean expense) {
        TransactionActivity.launch(this, requestCode, expense);
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

    private void doTransaction(TransactionTypeRealm typeRealm, int price, boolean expense) {
        if (!expense) {
            TransactionInRealm transaction = new TransactionInRealm();
            transaction.setId(1);
            transaction.setCurrentcy(new CurrentcyRealm());
            transaction.setTransactionType(0);
            transaction.setUnitPrice(price);
            transaction.setDateCreated(DateUtils.getCurrentDate());
            transaction.setTransactionCategory(typeRealm);
            RealmHelper.init(this).addObject(transaction, transactionInRealmChangeListener);
        } else {
            TransactionOutRealm transaction = new TransactionOutRealm();
            transaction.setId(1);
            transaction.setValue(price);
            transaction.setDateCreated(DateUtils.getCurrentDate());
            transaction.setTransactionCategory(typeRealm);
            transaction.setTransactionType(1);
            RealmHelper.init(this).addObject(transaction, transactionOutRealmChangeListener);
        }
    }

    private RealmChangeListener transactionInRealmChangeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(Realm realm) {
            TransactionInRealm transactionInRealm = RealmHelper.init(MainActivity.this).doQuery(TransactionInRealm.class).findFirst();
            mBinding.contentDashboard.currentBalance.setText(currentFormat(transactionInRealm.getUnitPrice()));
//            mBinding.contentDashboard.currentExpanse.setText(currentFormat(element.getValue()));
        }
    };

    private RealmChangeListener transactionOutRealmChangeListener = new RealmChangeListener<Realm>() {
        @Override
        public void onChange(Realm realm) {
            TransactionOutRealm transactionOutRealm = RealmHelper.init(MainActivity.this).doQuery(TransactionOutRealm.class).findFirst();
            mBinding.contentDashboard.currentExpanse.setText(currentFormat(transactionOutRealm.getValue()));
//            mBinding.contentDashboard.currentBalance.setText(String.format("%2d", element.getValue()));
        }
    };

    private String currentFormat(double price) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        return numberFormat.format(price);
    }
}
