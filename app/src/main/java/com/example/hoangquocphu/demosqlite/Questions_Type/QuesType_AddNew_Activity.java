package com.example.hoangquocphu.demosqlite.Questions_Type;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.R;

public class QuesType_AddNew_Activity extends AppCompatActivity {

    //region Khai báo biến toàn cục
    private EditText edQuestion;
    private Button btnSave, btnCancel;

    private QuestionsType questionsType;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_type__add_new_);

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

    public void btnCancelSaveQuestion(View view){
        this.onBackPressed();
    }

    public void SaveNewQuestions(){
        // Khởi tạo SQLite
        QuesType_DBHelper ques_Type_dbHelper = new QuesType_DBHelper(this);

        // Khai báo biến nhận giá trị trừ control
        String _question = this.edQuestion.getText().toString();

        // Thêm giá trị vào class
        this.questionsType = new QuestionsType(_question);

        // Thực hiện insert
        ques_Type_dbHelper.addQuestions(questionsType);

        // Thông báo thành công
        Toast.makeText(this, "Complete Save New Question", Toast.LENGTH_SHORT).show();

    }
    //endregion
}
