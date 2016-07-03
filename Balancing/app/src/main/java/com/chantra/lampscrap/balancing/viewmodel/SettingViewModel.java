package com.chantra.lampscrap.balancing.viewmodel;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.chantra.lampscrap.balancing.R;
import com.chantra.lampscrap.balancing.databinding.AlertBudgetBinding;
import com.chantra.lampscrap.balancing.respository.objects.SettingRealm;

/**
 * Created by phearom on 7/3/16.
 */
public class SettingViewModel {
    private SettingRealm model;

    public SettingViewModel(SettingRealm settingRealm) {
        this.model = settingRealm;
    }

    public SettingRealm getModel() {
        return this.model;
    }

    public String getName() {
        return this.model.getName();
    }

    public void launch(final Context context) {
        final AlertBudgetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.alert_budget, null, false);
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("Budget Amount")
                .setView(binding.getRoot())
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, binding.budgetAmount.getText().toString(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Dicard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        alertDialog.show();
    }
}
