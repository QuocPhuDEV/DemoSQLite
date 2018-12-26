package com.example.hoangquocphu.demosqlite.SignIn;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.R;

public class SignIn_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_);
    }

    public void btnSignIn(View view) {
        Toast.makeText(this, "Chức năng chưa phát triển, vui lòng chờ!", Toast.LENGTH_SHORT).show();
    }

    public void btnCancelSignIn(View view) {
        this.onBackPressed();
    }
}
