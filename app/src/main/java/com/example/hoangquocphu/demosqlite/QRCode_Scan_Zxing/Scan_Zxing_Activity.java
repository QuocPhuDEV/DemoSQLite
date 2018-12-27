package com.example.hoangquocphu.demosqlite.QRCode_Scan_Zxing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.JsonReader.JsonReader;
import com.example.hoangquocphu.demosqlite.QRCode.Scan;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Adapter;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_DBHelper;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Result_Activity;
import com.example.hoangquocphu.demosqlite.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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

    public static String TAG = null;
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

    //region LOAD MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.products_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSendData) {
//            Intent intent = new Intent(this, SignIn_Activity.class);
//            startActivity(intent);
            sendData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

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

    //region XỬ LÝ SEND DATA

    // Xử lý đa tiến trình
    public class MyJsonTask extends AsyncTask<String, JSONObject, Void> {

        public ProgressDialog dialog = new ProgressDialog(Scan_Zxing_Activity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Sending....");
            dialog.show();
        }

        @Override
        protected Void doInBackground(String... strings) {
            // Lấy URL truyền vào
            String url = strings[0];
            JSONObject jsonObject;
            try {
                // Đọc Json và chuyển về Object
                jsonObject = JsonReader.readFileJsonFromUrl(url);
                publishProgress(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
        }
    }

    // Đọc url
    public void readUrlLink(String _part, String _serial) {
        //String url = "http://192.168.200.191/api/carton/savelabel?partno=" + _part + "&serial=" + _serial + "";
        String url = "http://192.168.1.102/api/carton/savelabel?partno=" + _part + "&serial=" + _serial + "";
        String urls = url.replace("\u001d","");
        new MyJsonTask().execute(urls);
    }

    // Send nhiều dữ liệu
    public void sendData() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirm Request");
        alertDialog.setMessage("Are you sure send data to server ?");

        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ConfirmSendData();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }

        };

        alertDialog.setPositiveButton("Sure", clickListener);
        alertDialog.setNegativeButton("Cancel", clickListener);
        alertDialog.setIcon(R.drawable.userquestion);
        alertDialog.show();

    }

    // Add data từ textbox xuống listview
    public void btnAddData(View view) {
//        if (TextUtils.isEmpty(edPartNo.getText().toString()) || TextUtils.isEmpty(edSerial.getText().toString())) {
//            //Toast.makeText(this, "You need enter PartNo and Serial", Toast.LENGTH_SHORT).show();
//            edPartNo.setError("Enter PartNo");
//            edSerial.setError("Enter Serial");
//        } else {
//            listData.add(new String("Part No:\t" + edPartNo.getText().toString() + "\t" + "\t" + "\t"
//                    + "Serial:\t" + edSerial.getText().toString()));
//
//            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
//            listView.setAdapter(arrayAdapter);
//
//            edPartNo.setText(null);
//            edSerial.setText(null);
//            edPartNo.requestFocus();
//        }
    }

    // Confirm send data
    public void ConfirmSendData() {

        if (listItems.size() < 1) {
            Toast.makeText(this, "No have data to send to server!", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "------------------------------------- begin send data");
            for (int i = 0; i < listItems.size(); i++) {

                List<String> list = new ArrayList<>(Arrays.asList(listItems.get(i).toString().split(";")));
                MA_HANG = list.get(0).toString();
                SOID = list.get(1).toString();

                readUrlLink(MA_HANG, SOID);
            }
            Toast.makeText(Scan_Zxing_Activity.this, "Send data successfully!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "------------------------------------- end send data");
        }
    }

    //endregion
}
