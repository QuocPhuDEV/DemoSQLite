package com.example.hoangquocphu.demosqlite.Questions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hoangquocphu.demosqlite.Customer.Cus_DBHelper;
import com.example.hoangquocphu.demosqlite.Customer.Customer;
import com.example.hoangquocphu.demosqlite.R;

public class Ques_AddNew_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục
    private EditText edQuestion;
    private Button btnSave, btnCancel;

    private Questions questions;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques__add_new_);

        addObject();
        addEvent();
    }

    //region Ánh xạ đối tượng
    public void addObject() {
        edQuestion = (EditText) findViewById(R.id.edNewQuestion);
        btnSave = (Button) findViewById(R.id.btnSaveQuestions);
        btnCancel = (Button) findViewById(R.id.btnCancelQuestions);
    }
    //endregion

    //region Thêm sự kiện xử lý
    public void addEvent() {

    }
    //endregion

    //region Xử lý Action
    public void btnSaveQuestions(View view){
        SaveNewQuestions();
    }
    public void SaveNewQuestions(){
        // Khởi tạo SQLite
        Ques_DBHelper ques_dbHelper = new Ques_DBHelper(this);

        // Khai báo biến nhận giá trị trừ control
        String _question = this.edQuestion.getText().toString();

        // Thêm giá trị vào class
        this.questions = new Questions(_question);

        // Thực hiện insert
        ques_dbHelper.addQuestions(questions);
    }
    //endregion
}
