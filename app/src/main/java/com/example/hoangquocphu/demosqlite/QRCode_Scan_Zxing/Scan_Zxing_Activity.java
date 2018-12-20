package com.example.hoangquocphu.demosqlite.QRCode_Scan_Zxing;

import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.QRCode.Scan_Result_Activity;
import com.example.hoangquocphu.demosqlite.R;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.BarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan_Zxing_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private DecoratedBarcodeView qr_scanner_view;
    private CameraSettings cameraSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan__zxing_);

        zXingScannerView = new ZXingScannerView(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.relative_scan);
        layout.addView(zXingScannerView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //Toast.makeText(this, "" + result.getText(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Scan_Result_Activity.class);
        intent.putExtra("KQ", result.getText());
        startActivity(intent);
    }
}
