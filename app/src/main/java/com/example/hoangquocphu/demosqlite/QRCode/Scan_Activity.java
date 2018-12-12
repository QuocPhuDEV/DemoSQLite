package com.example.hoangquocphu.demosqlite.QRCode;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

public class Scan_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục
    SurfaceView surfaceView;
    CameraSource cameraSource;
    TextView textView;
    Button btnClearScan;
    ListView listView;
    BarcodeDetector barcodeDetector;

    MediaPlayer mediaPlayer;


    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
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
        listView = (ListView) findViewById(R.id.lvShowScanResult);
        btnClearScan = (Button) findViewById(R.id.btnClearScan);
        surfaceView = (SurfaceView) findViewById(R.id.cameraScan);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .setRequestedFps(30.0f)
                .build();
    }
    //endregion

    //region Thêm sự kiện xử lý
    public void addEvents() {
        ScanQRCode();
        btnClearScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                listItems.clear();
            }
        });
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
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(10);
                            //textView.setText(sparseArray.valueAt(0).displayValue);
                            String scanResult = sparseArray.valueAt(0).displayValue;
                            //cameraSource.stop();

                            // phát âm thanh
                            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.scanone);
                            mediaPlayer.start();

                            // Truyền biến sang màn hình kết quả
//                            Intent intent = new Intent(getApplicationContext(), Scan_Result_Activity.class);
//                            intent.putExtra("result", scanResult.toString());
//                            startActivity(intent);


                            // Kiểm tra sự tồn tại của mã scan trong
                            boolean resultCheck = listItems.contains(scanResult);

                            if (resultCheck == true) {
                                Toast.makeText(Scan_Activity.this, "QR Code đã scan!", Toast.LENGTH_SHORT).show();
                            } else {
                                listItems.add(scanResult);
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_scan_result, listItems);
                            listView.setAdapter(adapter);

                        }
                    });
                }
            }
        });
    }
    //endregion
}
