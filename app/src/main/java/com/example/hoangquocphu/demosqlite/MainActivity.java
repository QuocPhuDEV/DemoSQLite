package com.example.hoangquocphu.demosqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import com.example.hoangquocphu.demosqlite.Customer.Cus_Activity;
import com.example.hoangquocphu.demosqlite.Customer.Cus_List_Activity;
import com.example.hoangquocphu.demosqlite.HistoryServey.History_Servey_Activity;
import com.example.hoangquocphu.demosqlite.Location.Location_Activity;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Activity;
import com.example.hoangquocphu.demosqlite.QRCode_Scan_Zxing.Scan_Zxing_Activity;
import com.example.hoangquocphu.demosqlite.Questions_ExpandableList.Ques_AddNew_Activity;
import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_AddNew_Activity;
import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_List_Activity;


public class MainActivity extends AppCompatActivity {
    // region Khai báo biến toàn cục
    private Button btnCustomerServey;
    // endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addObject();
        addEvents();
    }

    //region Ánh xạ đối tượng
    public void addObject() {
        btnCustomerServey = (Button) findViewById(R.id.btnCustomerServey);
    }
    //endregion

    //region Thêm xử lý sự kiện
    public void addEvents() {

    }
    //endregion

    //region Khởi tạo menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemNewCustomer) {
            Intent intent = new Intent(MainActivity.this, Cus_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemCustomerList) {
            Intent intent = new Intent(MainActivity.this, Cus_List_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemQuestionTypeList) {
            Intent intent = new Intent(MainActivity.this, QuesType_List_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemNewQuestionType) {
            Intent intent = new Intent(MainActivity.this, QuesType_AddNew_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemScanCustomer) {
            Intent intent = new Intent(MainActivity.this, Scan_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemNewQuestion) {
            Intent intent = new Intent(MainActivity.this, Ques_AddNew_Activity.class);
            startActivity(intent);
            return false;
        } else if (id == R.id.itemGPS) {
            Intent intent = new Intent(MainActivity.this, Location_Activity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Xử lý Button Home
    public void btnCustomerServey(View view) {
        // Khai báo sử dụng popup menu
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnCustomerServey);
        popupMenu.getMenuInflater().inflate(R.menu.popup_customer_servey, popupMenu.getMenu());

        // Xử lý sự kiện click item
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.itemNewCustomer) {
                    Intent intent = new Intent(MainActivity.this, Cus_Activity.class);
                    startActivity(intent);
                    return false;
                } else if (id == R.id.itemListCustomer) {
                    Intent intent = new Intent(MainActivity.this, Cus_List_Activity.class);
                    startActivity(intent);
                    return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void btnScanQRCode(View view) {
//        Intent intent = new Intent(MainActivity.this, Scan_Activity.class);
//        startActivity(intent);

        Intent intent = new Intent(MainActivity.this, Scan_Zxing_Activity.class);
        startActivity(intent);
    }

    public void btnHistory(View view) {
        Intent intent = new Intent(MainActivity.this, History_Servey_Activity.class);
        startActivity(intent);
    }
    //endregion
}
