package com.example.hoangquocphu.demosqlite.QRCode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

public class Scan_Result_Activity extends AppCompatActivity {

    private TextView textView;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__result_);

        addObject();
        addEvents();
    }

    public void addObject() {
        textView = (TextView) findViewById(R.id.tvShowResultScan);
        btnBack = (Button) findViewById(R.id.btnScanBack);
    }

    public void addEvents() {

        textView.setText("QR Code không đúng định dạng" + "\n" + "Vui lòng thử lại !");

        //Intent intent = getIntent();
        //textView.setText(intent.getStringExtra("KQ"));
    }

    public void btnBackScan(View view) {
        this.onBackPressed();
    }
}
