package com.chantra.lampscrap.balancing.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.databinding.ActivityWelcomeBinding;
import com.chantra.lampscrap.balancing.utils.SessionManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {
    private ActivityWelcomeBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_welcome);

        mBinding.fullscreenContent.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchActivity();
            }
        },5000);
    }

    private void launchActivity(){
        if (SessionManager.init(this).isLogin()){
            startActivity(new Intent(this,MainActivity.class));
        }else{
            startActivity(new Intent(this,SignInActivity.class));
        }
        finish();
    }
}
