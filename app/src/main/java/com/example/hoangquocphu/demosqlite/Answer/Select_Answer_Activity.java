package com.example.hoangquocphu.demosqlite.Answer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.List;

public class Select_Answer_Activity extends AppCompatActivity {
    private TextView textView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__answer_);
        addObejct();
        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addObejct() {
        textView = (TextView) findViewById(R.id.tvQuestionShow);
        spinner = (Spinner) findViewById(R.id.spAnswer);
    }
    //endregion

    //region THÊM SỰ KIỆN
    public void addEvents() {
        loadForm();
        loadSpinner();
    }
    //endregion

    //region XỬ LÝ ACTION

    // Load form
    public void loadForm() {
        Intent intent = this.getIntent();
        String _questioon = intent.getStringExtra("question");
        textView.setText(_questioon);
    }

    // Xử lý load Spinner
    public void loadSpinner() {
        List<String> list = new ArrayList<>();
        list.add("TP.Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Nghệ An");
        list.add("An Giang");
        list.add("Đồng Nai");
        list.add("Quảng Bình");

        // thêm danh sách dữ liệu vào adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        // Gán giá trị cho spinner
        spinner.setAdapter(adapter);

        // xử lý sự kiện chọn item
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // Button Back
    public void btnBack(View view) {
        this.onBackPressed();
    }

    // Button Tiếp tục
    public void btnContinute(View view) {
//        Intent intent = new Intent(this, QuesType_List_Activity.class);
//        intent.putExtra()
    }
    //endregion
}
