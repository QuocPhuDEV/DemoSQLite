package com.example.hoangquocphu.demosqlite.Location;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.example.hoangquocphu.demosqlite.R;

public abstract class Location extends AppCompatActivity {
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDialogLoading();
    }

    private void initDialogLoading() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Đang load vị trí, vui lòng chờ.");
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    protected void showDialogLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    protected void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
