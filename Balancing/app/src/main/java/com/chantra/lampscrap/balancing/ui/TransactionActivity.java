package com.chantra.lampscrap.balancing.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.chantra.lampscrap.api.key.K;
import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.databinding.ActivityTransactionBinding;
import com.chantra.lampscrap.balancing.mapping.TType;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.TransactionInRealm;
import com.chantra.lampscrap.balancing.respository.objects.TransactionTypeRealm;
import com.chantra.lampscrap.balancing.ui.fragments.OnTypeFragmentListener;
import com.chantra.lampscrap.balancing.ui.fragments.TTypeAddFragment;
import com.chantra.lampscrap.balancing.ui.fragments.TTypeViewFragment;
import com.chantra.lampscrap.balancing.viewmodel.TTypeViewModel;

import com.chantra.lampscrap.balancing.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;



public class TransactionActivity extends AppCompatActivity implements OnTypeFragmentListener<TTypeViewModel> {
    private ActivityTransactionBinding mBinding;

    public static void launch(Activity activity, int requestCode, boolean expense, int typeId, String typeName) {
        Intent intent = new Intent(activity, TransactionActivity.class);
        intent.putExtra(K.IS_EXPENSE, expense);
        intent.putExtra(K.TYPE_ID, typeId);
        intent.putExtra(K.TYPE_NAME, typeName);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_transaction);
        setSupportActionBar(mBinding.toolBar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setDateView(getCurrentDate(Calendar.getInstance().getTime()));

        View specifictBox = findViewById(R.id.specifict_type_layout);
        if(typeId() > -1){
            specifictBox.setVisibility(View.VISIBLE);
            loadSpecific();
        }else{
            specifictBox.setVisibility(View.GONE);
            loadType();
        }


        mBinding.tvDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();
            }
        });

        mBinding.tranClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mBinding.buttonSpecifictType.setOnClickListener(new View.OnClickListener(){
          @Override
            public void onClick(View v){
              Intent intent = new Intent();
              //int type = intent.getExtras().getInt("type");
              //type = 1;
              //TTypeViewModel tTypeModel = new TTypeViewModel();
              //TTypeViewModel ttypeRealm = RealmHelper.init(getContext()).doQuery(TTypeViewModel.class).equalTo("id", type).findFirst();
              specifictExpanse(200,typeId() );
          }
        });

    }



    private void loadSpecific() {
        mBinding.buttonSpecifictType.setText("Add to " + typeName());
    }

    private void setDateView(String date) {
        mBinding.tvDateTime.setText(date);
    }

    private boolean isExpense() {
        try {
            return getIntent().getBooleanExtra(K.IS_EXPENSE, false);
        } catch (Exception e) {
            return false;
        }
    }

    private int typeId() {
        try {
            return getIntent().getIntExtra(K.TYPE_ID, -1);
        } catch (Exception e) {
            return -1;
        }
    }

    private String typeName() {
        try {
            return getIntent().getStringExtra(K.TYPE_NAME);
        } catch (Exception e) {
            return "";
        }
    }

    private void loadType() {
        TTypeViewFragment fragment = new TTypeViewFragment();
        fragment.setTType(isExpense() ? K.TType.EXPENSE : K.TType.INCOME);

        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mBinding.ttypeContainer.getId(), fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transaction, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_today:
                datePicker();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void datePicker() {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(TransactionActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, monthOfYear);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setDateView(getCurrentDate(c.getTime()));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        // Setting dialogview
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true);
        dialog.show();
    }

    private String getCurrentDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM");
        String currentDate = formatter.format(date);
        return currentDate;
    }



    @Override
    public void onAction(int action, TTypeViewModel item) {
        TType tType = new TType();
        switch (action) {
            case ACTION_ADDED:
            case ACTION_SELECTED:
                tType.put(item.getModel());
                break;
            case ACTION_GO_ADD:
                mBinding.tranNote.animate().alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        mBinding.tranNote.setVisibility(View.GONE);
                    }
                });
                loadFragment(new TTypeAddFragment());
                break;
        }

        if (null == item)
            return;

        if (TextUtils.isEmpty(mBinding.edtTransactionAmount.getText().toString().trim()))
            return;

        Intent intent = new Intent();
        Bundle bundle = tType.toData();
        bundle.putString("date", String.valueOf(mBinding.tvDateTime.getText()));
        bundle.putInt("price", Integer.valueOf(mBinding.edtTransactionAmount.getText().toString()));
        bundle.putString("description", String.valueOf(mBinding.tranNote.getText()));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void specifictExpanse(int action, int item_id) {
        RealmResults<TransactionTypeRealm> typeRealms = RealmHelper.init(this).doQuery(TransactionTypeRealm.class).equalTo("id", item_id).findAll();

        TType tType = new TType();

        for (TransactionTypeRealm tTypeObj : typeRealms) {
            tType.put(tTypeObj);
        }

        Intent intent = new Intent();
        Bundle bundle = tType.toData();
        bundle.putString("date", String.valueOf(mBinding.tvDateTime.getText()));
        bundle.putInt("price", Integer.valueOf(mBinding.edtTransactionAmount.getText().toString()));
        bundle.putString("description", String.valueOf(mBinding.tranNote.getText()));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }
}
