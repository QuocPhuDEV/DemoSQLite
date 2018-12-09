package com.example.hoangquocphu.demosqlite.QRCode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Scan_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    BarcodeDetector barcodeDetector;

    MediaPlayer mediaPlayer;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_);

        addObject();
        addEvents();
    }

    //region Ánh xạ đối tượng
    public void addObject() {
        textView = (TextView) findViewById(R.id.tvShowScan);
        surfaceView = (SurfaceView) findViewById(R.id.cameraScan);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480).build();
    }
    //endregion

    //region Thêm sự kiện xử lý
    public void addEvents() {
        ScanQRCode();
    }
    //endregion

    //region Xử lý action
    public void ScanQRCode() {
        // Xử lý camera scan
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        // Xử lý barcode Scan
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> sparseArray = detections.getDetectedItems();
                if (sparseArray.size() != 0) {
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
//                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                            vibrator.vibrate(1);
                            //textView.setText(sparseArray.valueAt(0).displayValue);
                            String scanResult = sparseArray.valueAt(0).displayValue;
                            cameraSource.stop();

                            // phát âm thanh
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.beep);
                            mediaPlayer.start();

                            // Truyền biến sang màn hình kết quả
                            Intent intent = new Intent(getApplicationContext(), Scan_Result_Activity.class);
                            intent.putExtra("result", scanResult.toString());
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
    //endregion
}
