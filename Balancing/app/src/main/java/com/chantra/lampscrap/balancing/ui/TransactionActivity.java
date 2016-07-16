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
import com.chantra.lampscrap.balancing.ui.fragments.OnTypeFragmentListener;
import com.chantra.lampscrap.balancing.ui.fragments.TTypeAddFragment;
import com.chantra.lampscrap.balancing.ui.fragments.TTypeViewFragment;
import com.chantra.lampscrap.balancing.viewmodel.TTypeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity implements OnTypeFragmentListener<TTypeViewModel> {
    private ActivityTransactionBinding mBinding;

    public static void launch(Activity activity, int requestCode, boolean expense, int typeId) {
        Intent intent = new Intent(activity, TransactionActivity.class);
        intent.putExtra(K.IS_EXPENSE, expense);
        intent.putExtra(K.TYPE_ID, typeId);
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
        loadType();

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
        bundle.putInt("price", Integer.valueOf(mBinding.edtTransactionAmount.getText().toString()));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
