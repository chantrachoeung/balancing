package com.chantra.lampscrap.balancing.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.databinding.ActivitySignUpBinding;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.UserRealm;
import com.chantra.lampscrap.balancing.utils.DateUtils;

import io.realm.RealmChangeListener;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        mBinding.linkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        mBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCreateAccount();
            }
        });
    }

    private void goLogin() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goLogin();
    }

    private void doCreateAccount() {
        String name = mBinding.inputUserName.getText().toString().trim();
        String pass = mBinding.inputPassword.getText().toString();
        String confirm_pass = mBinding.inputConfirmPassword.getText().toString();

        mBinding.usernameWrapper.setErrorEnabled(false);
        mBinding.passwordWrapper.setErrorEnabled(false);
        mBinding.confirmPasswordWrapper.setErrorEnabled(false);

        if (TextUtils.isEmpty(name)) {
            mBinding.usernameWrapper.setErrorEnabled(true);
            mBinding.usernameWrapper.setError("Require username!");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            mBinding.passwordWrapper.setErrorEnabled(true);
            mBinding.usernameWrapper.setError("Require password!");
            return;
        }

        if (!pass.equals(confirm_pass)) {
            mBinding.confirmPasswordWrapper.setErrorEnabled(true);
            mBinding.confirmPasswordWrapper.setError("Password not match !");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating account...");
        progressDialog.show();

        final UserRealm userRealm = new UserRealm();
        userRealm.setId(RealmHelper.init(this).autoIncrement(UserRealm.class, "id"));
        userRealm.setName(name);
        userRealm.setPassword(pass);
        userRealm.setSignUpDate(DateUtils.getCurrentDate());
        RealmHelper.init(this).addObject(userRealm, new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                if (null != progressDialog)
                    progressDialog.dismiss();
                goLogin();
            }
        });
    }
}

