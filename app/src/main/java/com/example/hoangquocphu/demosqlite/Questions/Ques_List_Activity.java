package com.example.hoangquocphu.demosqlite.Questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.hoangquocphu.demosqlite.Customer.Cus_DBHelper;
import com.example.hoangquocphu.demosqlite.Customer.Customer;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.List;

public class Ques_List_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private ListView listView;

    private final List<Questions> questionsList = new ArrayList<Questions>();
    private ArrayAdapter<Questions> arrayAdapter;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques__list_);

        addObjects();
        addEvents();
    }

    //region Ánh xạ đối tượng
    public void addObjects() {
        listView = (ListView) findViewById(R.id.lvQuestion);
    }
    //endregion

    //region Sự kiện xử lý
    public void addEvents() {
        loadListView();
    }
    //endregion

    //region xử lý Action
    public void loadListView() {
        // Khởi tạo sqlite
        Ques_DBHelper ques_dbHelper = new Ques_DBHelper(this);

        // Lấy danh sách các record có trong bảng
        List<Questions> list = ques_dbHelper.getAllQuestions();
        this.questionsList.addAll(list);

        // tạo list adapter
        this.arrayAdapter = new ArrayAdapter<Questions>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.questionsList);

        // đăng ký adapter cho listView
        this.listView.setAdapter(this.arrayAdapter);

        // đăng ký context menu cho listview
        registerForContextMenu(this.listView);
    }
    //endregion
}
