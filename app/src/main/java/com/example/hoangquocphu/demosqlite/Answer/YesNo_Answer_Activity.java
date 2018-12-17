package com.example.hoangquocphu.demosqlite.Answer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class YesNo_Answer_Activity extends AppCompatActivity {
    private TextView textView;
    private RadioButton rdoCo, rdoKhong, rdoKhongBiet;
    private String Question = "";
    private String Answer = "";

    // Biến constant được dùng để định danh dữ liệu được truyền giữa các Activity
    public static final String QUESTION = "QUESTION";
    public static final String ANSWER = "ANSWER";
    public static final String TIME = "TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yesno_answer_);
        addObejct();
        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addObejct() {
        textView = (TextView) findViewById(R.id.tvQuestionShow);
        rdoCo = (RadioButton) findViewById(R.id.rdoCo);
        rdoKhong = (RadioButton) findViewById(R.id.rdoKhong);
        rdoKhongBiet = (RadioButton) findViewById(R.id.rdoKhongBiet);
    }
    //endregion

    //region THÊM SỰ KIỆN
    public void addEvents() {
        loadForm();

    }
    //endregion

    //region XỬ LÝ ACTION

    // Load form
    public void loadForm() {
        Intent intent = this.getIntent();
        String _questioon = intent.getStringExtra("question");
        textView.setText(_questioon);
        Question = _questioon;
    }

    // Xử lý Radio Button
    public void onRadioButtonClick(View view) {
        // Kiểm tra button được check
        boolean checked = ((RadioButton) view).isChecked();

        // Xử lý khi check radion button
        switch (view.getId()) {
            case R.id.rdoCo:
                if (checked) {
                    rdoKhong.setChecked(false);
                    rdoKhongBiet.setChecked(false);
                    Answer = rdoCo.getText().toString();
                }
                break;

            case R.id.rdoKhong:
                if (checked) {
                    rdoCo.setChecked(false);
                    rdoKhongBiet.setChecked(false);
                    Answer = rdoKhong.getText().toString();
                }
                break;
            case R.id.rdoKhongBiet:
                if (checked) {
                    rdoCo.setChecked(false);
                    rdoKhong.setChecked(false);
                    Answer = rdoKhongBiet.getText().toString();
                }
                break;
        }

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

        // truyền dữ liệu vào intent
        intent.putExtra(QUESTION, Question);
        intent.putExtra(ANSWER, Answer);
        intent.putExtra(TIME, dateTimeNow);

        // Đặt resultCode là Activity.RESULT_OK to
        // thể hiện đã thành công và có chứa kết quả trả về
        setResult(Activity.RESULT_OK, intent);

        //gọi hàm finish() để đóng Activity hiện tại và trở về Activity trước.
        finish();

    }
    //endregion
}
