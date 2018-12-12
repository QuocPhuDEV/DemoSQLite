package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.MainActivity;
import com.example.hoangquocphu.demosqlite.QRCode.Scan_Activity;
import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_DBHelper;
import com.example.hoangquocphu.demosqlite.Questions_Type.QuestionsType;
import com.example.hoangquocphu.demosqlite.R;

public class Ques_AddNew_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private EditText edQuestion;
    private Button btnSave, btnCancel;

    private Ques ques;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_add_new_);
        addObject();
    }

    //region Ánh xạ đối tượng
    public void addObject() {
        edQuestion = (EditText) findViewById(R.id.edNewQuestion);
        btnSave = (Button) findViewById(R.id.btnSaveQuestionsYN);
        btnCancel = (Button) findViewById(R.id.btnCancelQuestionsYN);
    }
    //endregion

    //region Xử lý Action
    public void btnSaveNewQuestions(View view) {
        SaveNewQuestions();
    }

    public void btnCancelNewSaveQuestion(View view) {
        this.onBackPressed();
    }

    public void SaveNewQuestions() {
        // Khởi tạo SQLite
        Ques_DBHelper ques_dbHelper = new Ques_DBHelper(this);

        // Khai báo biến nhận giá trị trừ control
        String _question = this.edQuestion.getText().toString();

        // Thêm giá trị vào class
        this.ques = new Ques(_question);

        // Thực hiện insert
        ques_dbHelper.addQuestions(ques, getApplicationContext());

        //
        Intent intent = new Intent(getApplicationContext(), Ques_Detail_List_Activity.class);
        startActivity(intent);
    }
    //endregion
}
