package com.example.hoangquocphu.demosqlite.Answer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.hoangquocphu.demosqlite.R;

public class Input_Answer_Activity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input__answer_);
        addObejct();
        addEvents();
    }

    //region ÁNH XẠ ĐỐI TƯỢNG
    public void addObejct() {
        textView = (TextView) findViewById(R.id.tvQuestionShow);
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
