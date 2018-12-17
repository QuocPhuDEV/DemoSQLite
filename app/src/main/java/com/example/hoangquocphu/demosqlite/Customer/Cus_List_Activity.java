package com.example.hoangquocphu.demosqlite.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.List;

public class Cus_List_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private ListView listView;
    private Cus_Adapter cus_adapter;
    private final List<Customer> customerList = new ArrayList<Customer>();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus__list_);

        addObjects();
        addEvents();
    }

    //region Ánh xạ đối tượng
    public void addObjects() {
        listView = (ListView) findViewById(R.id.lvCustomer);
    }
    //endregion

    //region Sự kiện xử lý
    public void addEvents() {
        loadListView();
        selectItemCustomer();
    }
    //endregion

    //region xử lý Action
    // Load danh sách khách hàng
    public void loadListView() {
        // Khởi tạo sqlite
        Cus_DBHelper cus_dbHelper = new Cus_DBHelper(this);
        cus_dbHelper.createDefaultCustomer(getApplicationContext());

        // Lấy danh sách các record có trong bảng
        List<Customer> list = cus_dbHelper.getAllCustomer();
        this.customerList.addAll(list);

        // tạo list adapter
        cus_adapter = new Cus_Adapter(getApplicationContext(), R.layout.item_customer, customerList);

        // gán giá trị từ adapter cho listview
        this.listView.setAdapter(cus_adapter);

        // đăng ký context menu cho listview
        registerForContextMenu(this.listView);
    }

    // Xử lý khi click chọn item khách hàng
    public void selectItemCustomer() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), QuesType_List_Activity.class);
                // Lấy giá trị current khi click vào item trên list;
                intent.putExtra("CUS_NAME", customerList.get(i).toString());
                startActivity(intent);
            }
        });
    }
    //endregion
}
