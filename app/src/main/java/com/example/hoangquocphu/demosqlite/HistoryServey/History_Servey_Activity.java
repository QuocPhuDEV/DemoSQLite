package com.example.hoangquocphu.demosqlite.HistoryServey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hoangquocphu.demosqlite.Answer.An_DBHelper;
import com.example.hoangquocphu.demosqlite.Answer.Answer;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.List;

public class History_Servey_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private ListView listView;
    private His_Adapter his_adapter;
    private final List<Answer> answerList = new ArrayList<Answer>();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history__servey_);
        this.addObjects();
        this.addEvents();
    }

    //region Ánh xạ đối tượng
    public void addObjects() {
        listView = (ListView) findViewById(R.id.lvShowHistory);
    }
    //endregion

    //region Sự kiện xử lý
    public void addEvents() {
        loadListView();
    }

    //endregion

    //region xử lý Action
    // Load danh sách khách hàng
    public void loadListView() {
        // Khởi tạo sqlite
        An_DBHelper an_dbHelper = new An_DBHelper(this);
        an_dbHelper.createDefaultHistory(getApplicationContext());

        // Lấy danh sách các record có trong bảng
        List<Answer> list = an_dbHelper.getAllAnswer();
        this.answerList.addAll(list);

        // tạo list adapter
        his_adapter = new His_Adapter(getApplicationContext(), R.layout.item_history_servey, answerList);

        // gán giá trị từ adapter cho listview
        this.listView.setAdapter(his_adapter);

        // đăng ký context menu cho listview
        registerForContextMenu(this.listView);
    }

    // Xử lý khi click chọn item khách hàng
//    public void selectItemCustomer() {
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), QuesType_List_Activity.class);
//                // Lấy giá trị current khi click vào item trên list;
//                intent.putExtra("CUS_NAME", customerList.get(i).toString());
//                startActivity(intent);
//            }
//        });
//    }
    //endregion
}
