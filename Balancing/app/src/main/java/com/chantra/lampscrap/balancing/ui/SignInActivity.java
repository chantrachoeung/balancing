package com.chantra.lampscrap.balancing.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.databinding.ActivitySignInBinding;
import com.chantra.lampscrap.balancing.respository.RealmHelper;
import com.chantra.lampscrap.balancing.respository.objects.UserRealm;
import com.chantra.lampscrap.balancing.utils.DateUtils;
import com.chantra.lampscrap.balancing.utils.SessionManager;

import java.util.UUID;

import io.realm.Case;
import io.realm.Realm;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding mBinding;
    private UserRealm userRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
        mBinding.signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        mBinding.linkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignUp();
            }
        });
    }

    private void goSignUp() {
        startActivity(new Intent(this, SignUpActivity.class));
        finish();
    }

    private void doLogin() {
        final String username = mBinding.userName.getText().toString();
        final String password = mBinding.password.getText().toString();

        mBinding.usernameWrapper.setErrorEnabled(false);
        mBinding.passwordWrapper.setErrorEnabled(false);

        if (TextUtils.isEmpty(username)) {
            mBinding.usernameWrapper.setErrorEnabled(true);
            mBinding.usernameWrapper.setError("Require user name!");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mBinding.passwordWrapper.setErrorEnabled(true);
            mBinding.passwordWrapper.setError("Require password!");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Waiting for login...");
        progressDialog.show();
        RealmHelper.init(this).getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String accessToken = UUID.randomUUID().toString();
                userRealm = realm.where(UserRealm.class).beginGroup().equalTo("name", username, Case.SENSITIVE).equalTo("password", password, Case.SENSITIVE).endGroup().findFirst();
                if (null != userRealm) {
                    userRealm.setSignInDate(DateUtils.getCurrentDate());
                    userRealm.setAccessToken(accessToken);
                    SessionManager.init(SignInActivity.this).setAccessToken(accessToken);
                } else {
                    throw new RuntimeException("Do you already have an account?");
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if (null != progressDialog)
                    progressDialog.dismiss();

                SessionManager.init(SignInActivity.this).setIsLogin(true);
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if (null != progressDialog)
                    progressDialog.dismiss();

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

