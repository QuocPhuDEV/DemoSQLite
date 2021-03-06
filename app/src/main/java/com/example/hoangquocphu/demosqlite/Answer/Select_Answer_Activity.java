package com.example.hoangquocphu.demosqlite.Answer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Select_Answer_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục
    private TextView textView;
    private Spinner spinner;

    // Khai báo biến nhận dữ liệu câu hỏi và trả lời
    private String Question = "";
    private String Answer = "";

    // Vị trí của item câu hỏi đã chọn
    private int position = 0;

    // khai báo biến constant để định danh dữ liệu được truyền giữa các Activity
    public static final String QUESTION = "QUESTION";
    public static final String ANSWER = "ANSWER";
    public static final String TIME = "TIME";

    public static final String POSITION = "POSITION";
    //endregion

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
        String _question = intent.getStringExtra("question");
        position = intent.getIntExtra("position", 0);

        textView.setText(_question);
        Question = _question;
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
// Tạo intent mới để chứa dữ liệu
        final Intent intent = new Intent();

        // Lấy ngày tháng hiện tại
        // Khai báo đối tượng today kiểu Date
        Date today = new Date(System.currentTimeMillis());
        // Khai báo định dạng
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
        // Gán thời gian
        String dateTimeNow = dateFormat.format(today.getTime());

        // Gán giá trị câu trả lời
        Answer = spinner.getSelectedItem().toString();
        // truyền dữ liệu vào intent
        intent.putExtra(QUESTION, Question);
        intent.putExtra(ANSWER, Answer);
        intent.putExtra(TIME, dateTimeNow);
        intent.putExtra(POSITION, position);

        // Đặt resultCode là Activity.RESULT_OK to
        // thể hiện đã thành công và có chứa kết quả trả về
        setResult(Activity.RESULT_OK, intent);

        //gọi hàm finish() để đóng Activity hiện tại và trở về Activity trước.
        finish();
    }
    //endregion
}
