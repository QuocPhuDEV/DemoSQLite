package com.example.hoangquocphu.demosqlite.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.JsonReader.JsonReader;
import com.example.hoangquocphu.demosqlite.MainActivity;
import com.example.hoangquocphu.demosqlite.R;
import com.example.hoangquocphu.demosqlite.SignIn.SignIn_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Login_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private EditText edUser, edPassword;
    private Button btnLogion, btnCancel;

    public String MaNV = "";
    public String TenNV = "";

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addEvents() {
        edUser = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
        btnLogion = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }
    //endregion

    //region LOAD MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.login_menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemSignIn) {
            Intent intent = new Intent(this, SignIn_Activity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region XỬ LÝ EVENTS

    // Xử lý đa tiến trình
    public class MyJsonTask extends AsyncTask<String, JSONObject, Void> {
        public ProgressDialog dialog = new ProgressDialog(Login_Activity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait....");
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
        protected void onProgressUpdate(JSONObject... values) {
            super.onProgressUpdate(values);

            // Get dữ liệu
            JSONObject jsonObject = values[0];
            try {
                // Kiểm tra có tồn tại từ khoá "result" trong chuỗi Json hay ko
                //jsonObject.has("result") &&
                if (jsonObject.getString("message").equals("Wrong username, password")) {
                    Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT).show();
                } else {

                    // Khai báo mới 1 JSONObject để lưu mảng "result"
                    JSONObject objectAccount = jsonObject.getJSONObject("result");

                    // Tìm các thông tin
                    if (objectAccount.has("MaNV")) {
                        MaNV = objectAccount.getString("MaNV");
                    }
                    if (objectAccount.has("TenNV")) {
                        TenNV = objectAccount.getString("TenNV");
                    }

                    // Gọi form main và gán giá trị
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("MaNV", MaNV);
                    intent.putExtra("TenNV", TenNV);
                    startActivity(intent);
                }

            } catch (JSONException e) {
                Toast.makeText(Login_Activity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dialog.dismiss();
        }
    }

    // Xử lý button
    public void btnLoginAccount(View view) {
        try {
            readUrlLink();
        } catch (Exception ex) {
            Toast.makeText(this, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    // Xử lý button cancel
    public void btnCancel(View view) {
        //System.exit(0);
        getPhoneNumber();
    }

    // Đọc url
    public void readUrlLink() {
        String url = "http://192.168.200.190/api/user/login?userid=" + edUser.getText() + "&password=" + edPassword.getText();
        //String url = "http://192.168.1.102/api/user/login?userid=" + edUser.getText() + "&password=" + edPassword.getText();
        new MyJsonTask().execute(url);
    }

    // Lấy sđt
    public void getPhoneNumber() {

        // Lưu ý khi lấy số điện thoại
        // Cấp đủ quyền cho nó READ_PHONE_STATE
        // Máy cần lấy sđt phải khai báo số điện thoại trong máy

        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, " You are not granted permission.", Toast.LENGTH_SHORT).show();
            return;
        }
        String mPhoneNumber = manager.getLine1Number();
        Toast.makeText(this, "" + mPhoneNumber, Toast.LENGTH_SHORT).show();

//        AccountManager am = AccountManager.get(this);
//        Account[] accounts = am.getAccounts();
//
//        for (Account ac : accounts) {
//            String acname = ac.name;
//            String actype = ac.type;
//            // Take your time to look at all available accounts
//            Toast.makeText(this, "" + acname + " " + actype, Toast.LENGTH_SHORT).show();
//        }

    }
    //endregion
}
