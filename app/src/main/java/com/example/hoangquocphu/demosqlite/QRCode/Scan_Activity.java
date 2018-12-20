package com.example.hoangquocphu.demosqlite.QRCode;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import com.example.hoangquocphu.demosqlite.Answer.An_DBHelper;
import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Scan_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục

    // Khai báo đối tượng scan
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private TextView textView;
    private Button btnClearScan;
    public ListView listView;
    private BarcodeDetector barcodeDetector;
    private MediaPlayer mediaPlayer;


    // Khai báo mảng chứa dữ liệu
    private List<Scan> listItems = new ArrayList<Scan>();
    private Scan_Adapter scan_adapter;
    private Scan scan;

    // Khái báo biến chứa dữ liệu
    private String MA_HANG = "";
    private String SOID = "";
    private String SCAN_TIME = "";

    public static final int REQUEST_ID_ACCESS_COURSE_FINE_LOCATION = 100;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_);

        addObject();
        addEvents();
        loadListView();
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

                            String scanResult = sparseArray.valueAt(0).displayValue;

                            // Kiểm tra định dạng của QRCode Scan
                            if (checkFormatQRCode(scanResult)) {

                                // Kiểm tra sự tồn tại của mã scan trong
                                if (checkExist(scanResult) != 0) {
                                    //Toast.makeText(Scan_Activity.this, "QR Code đã scan!", Toast.LENGTH_SHORT).show();
                                } else {

                                    // rung sau khi scan
                                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                    vibrator.vibrate(10);

                                    // phát âm thanh
                                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.scanone);
                                    mediaPlayer.start();

                                    // Insert xuống database
                                    SaveDataScan(scanResult);

                                    // Load lại lưới
                                    loadListView();
                                }
                            } else {
                                cameraSource.stop();

                                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);
                                mediaPlayer.start();

                                Intent intent = new Intent(getApplicationContext(), Scan_Result_Activity.class);
                                startActivity(intent);
                            }

                        }
                    });
                }
            }
        });

    }

    // Thêm dữ liệu đã scan vào database
    public void SaveDataScan(String scanResult) {
        // Khởi tạo SQLite
        Scan_DBHelper scan_dbHelper = new Scan_DBHelper(this);

        //Thêm giá trị vào class
        DataScan(scanResult);
        this.scan = new Scan(MA_HANG, SOID, SCAN_TIME);

        // Thực hiện insert
        scan_dbHelper.addScan(scan);


    }

    // Xử lý dữ liệu sau khi bấm scan
    public void DataScan(String scanResult) {
        List<String> list = new ArrayList<>(Arrays.asList(scanResult.split(";")));

        // Lấy ngày tháng hiện tại
        // Khai báo đối tượng today kiểu Date
        Date today = new Date(System.currentTimeMillis());
        // Khai báo định dạng
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        // Gán thời gian
        String dateTimeNow = dateFormat.format(today.getTime());

        MA_HANG = list.get(1).toString();
        SOID = list.get(7).toString();
        SCAN_TIME = dateTimeNow;
    }

    // Đổ dữ liệu lên lưới
    public void loadListView() {
        // Khởi tạo sqlite
        Scan_DBHelper scan_dbHelper = new Scan_DBHelper(this);

        // Lấy danh sách các record có trong bảng
        List<Scan> scanList = scan_dbHelper.getAllScanData();
        this.listItems.clear();
        this.listItems.addAll(scanList);

        // Tạo adapter
        scan_adapter = new Scan_Adapter(getApplicationContext(), R.layout.item_scan, listItems);

        // gán giá trị từ adapter cho listview
        this.listView.setAdapter(scan_adapter);

        // đăng ký context menu cho listview
        // registerForContextMenu(this.listView);
    }

    // Kiểm tra tồn tại dữ liệu
    public int checkExist(String scanResult) {
        // Khởi tạo sqlite
        Scan_DBHelper scan_dbHelper = new Scan_DBHelper(this);

        DataScan(scanResult);
        int count = 0;
        count = scan_dbHelper.getCountScan(MA_HANG, SOID);

        return count;
    }

    // Xóa hết dữ liệu
    public void deleteAllData(View view) {
        Scan_DBHelper scan_dbHelper = new Scan_DBHelper(this);
        scan_dbHelper.deleteAllData();
        loadListView();
    }

    // Kiểm tra định dạng QRCode Scan
    public boolean checkFormatQRCode(String scanResult) {
        List<String> list = new ArrayList<>(Arrays.asList(scanResult.split(";")));
        if (list.size() < 8) {
            return false;
        } else {
            return true;
        }
    }

    // Check quyền truy cập camera
    private void askPermissionsAndShowMyLocation() {

        try {
            // Nếu API> = 23, hiển thị thông báo hỏi người dùng về quyền truy cập
            if (Build.VERSION.SDK_INT >= 23) {
                int accessCamera
                        = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


                if (accessCamera != PackageManager.PERMISSION_GRANTED) {
                    // Hỏi quyền từ người dùng
                    String[] permissions = new String[]{Manifest.permission.CAMERA};
                    // Hiển thị dialog xác nhận
                    ActivityCompat.requestPermissions(this, permissions,
                            REQUEST_ID_ACCESS_COURSE_FINE_LOCATION);

                    return;
                }
            }
        } catch (Exception e) {

        }
    }
    //endregion
}
