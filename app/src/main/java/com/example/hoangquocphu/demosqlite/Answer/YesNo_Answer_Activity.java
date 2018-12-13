package com.example.hoangquocphu.demosqlite.Answer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

public class YesNo_Answer_Activity extends AppCompatActivity {
    private TextView textView;
    private RadioButton rdoCo, rdoKhong, rdoKhongBiet;

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
                }
                break;

            case R.id.rdoKhong:
                if (checked) {
                    rdoCo.setChecked(false);
                    rdoKhongBiet.setChecked(false);
                }
                break;
            case R.id.rdoKhongBiet:
                if (checked) {
                    rdoCo.setChecked(false);
                    rdoKhong.setChecked(false);
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
//        Intent intent = new Intent(this, QuesType_List_Activity.class);
//        intent.putExtra()
    }
    //endregion
}
