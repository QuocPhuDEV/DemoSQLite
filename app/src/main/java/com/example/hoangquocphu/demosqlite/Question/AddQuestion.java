package com.example.hoangquocphu.demosqlite.Question;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.R;

public class AddQuestion extends AppCompatActivity {
    Question question;
    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;

    private int mode;
    private EditText edQuestion;

    private boolean needRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        this.edQuestion = (EditText) this.findViewById(R.id.edQuestion);

        Intent intent = this.getIntent();
        this.question = (Question) intent.getSerializableExtra("question");
        if (question == null) {
            this.mode = MODE_CREATE;
        } else {
            this.mode = MODE_EDIT;
            this.edQuestion.setText(question.getQuestion());
        }
    }

    // Người dùng Click vào nút Save.
    public void buttonSaveClicked(View view) {
        DBHelper db = new DBHelper(this);

        String edquestion = this.edQuestion.getText().toString();


        if (edquestion.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter new question", Toast.LENGTH_LONG).show();
            return;
        }

        if (mode == MODE_CREATE) {
            this.question = new Question(edquestion);
            db.addQuestion(question);
        } else {
            this.question.setQuestion(edquestion);
            db.updateQuestion(question);
        }

        this.needRefresh = true;
        // Trở lại MainActivity.
        this.onBackPressed();
    }

    // Khi người dùng Click vào button Cancel.
    public void buttonCancelClicked(View view) {
        // Không làm gì, trở về MainActivity.
        this.onBackPressed();
    }

    // Khi Activity này hoàn thành,
    // có thể cần gửi phản hồi gì đó về cho Activity đã gọi nó.
    @Override
    public void finish() {

        // Chuẩn bị dữ liệu Intent.
        Intent data = new Intent();
        // Yêu cầu MainActivity refresh lại ListView hoặc không.
        data.putExtra("needRefresh", needRefresh);

        // Activity đã hoàn thành OK, trả về dữ liệu.
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}
