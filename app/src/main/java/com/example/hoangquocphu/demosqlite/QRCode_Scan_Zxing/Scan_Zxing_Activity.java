package com.example.hoangquocphu.demosqlite.QRCode_Scan_Zxing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.QRCode.Scan;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Adapter;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_DBHelper;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Result_Activity;
import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan_Zxing_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    //region Khai báo biến toàn cục
    private ZXingScannerView zXingScannerView;

    // Khai báo đối tượng scan
    private TextView textView;
    private Button btnClearScan;
    public ListView listView;
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
        setContentView(R.layout.activity_scan__zxing_);

        addObject();
        loadListView();
    }

    //region Ánh xạ đối tượng
    public void addObject() {
        zXingScannerView = new ZXingScannerView(this);

        textView = (TextView) findViewById(R.id.tvShowScan);
        listView = (ListView) findViewById(R.id.lvShowScanResult);
        btnClearScan = (Button) findViewById(R.id.btnClearScan);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_Scan);
        layout.clearAnimation();
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
        try {
            // Khai báo biến nhận giá trị scan
            String scanResult = result.getText();

            // Kiểm tra định dạng của QRCode Scan
            if (checkFormatQRCode(scanResult)) {

                // Kiểm tra sự tồn tại của mã scan
                if (checkExist(scanResult) != 0) {
                    // Tiếp tục scan
                    zXingScannerView.resumeCameraPreview(this);
                } else {
                    // XỬ LÝ KHI SCAN HỢP LỆ
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

                    // Tiếp tục scan
                    zXingScannerView.resumeCameraPreview(this);
                }
            } else {
                zXingScannerView.stopCamera();

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.error);
                mediaPlayer.start();

                Intent intent = new Intent(getApplicationContext(), Scan_Result_Activity.class);
                startActivity(intent);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //region XỬ LÝ ACTION

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
        onResume();
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

    //endregion
}
