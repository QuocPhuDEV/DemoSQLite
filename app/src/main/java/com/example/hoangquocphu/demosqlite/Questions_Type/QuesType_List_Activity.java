package com.example.hoangquocphu.demosqlite.Questions_Type;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.Answer.An_DBHelper;
import com.example.hoangquocphu.demosqlite.Answer.Answer;
import com.example.hoangquocphu.demosqlite.Answer.CheckBox_Answer_Activity;
import com.example.hoangquocphu.demosqlite.Answer.Input_Answer_Activity;
import com.example.hoangquocphu.demosqlite.Answer.Select_Answer_Activity;
import com.example.hoangquocphu.demosqlite.Answer.YesNo_Answer_Activity;
import com.example.hoangquocphu.demosqlite.Questions_ExpandableList.Ques_Detail_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class QuesType_List_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private static final int REQUEST_CODE_EXAMPLE = 0x9345;
    public static final String fQUESTION = "QUESTION";
    public static final String fANSWER = "ANSWER";
    public static final String fTIME = "TIME";

    private ListView listView;
    private Answer answer;

    private final List<QuestionsType> questionsTypeList = new ArrayList<QuestionsType>();
    private ArrayAdapter<QuestionsType> arrayAdapter;

    // Khai báo hằng số kiểu câu hỏi
    private final String MODE_YESNO = "(YN)";
    private final String MODE_CK = "(CK)";
    private final String MODE_IP = "(IP)";
    private final String MODE_SE = "(SE)";

    // Khai báo tên khách hàng
    public String CUS_NAME = "";
    public String QUESTION_NAME = "";
    public String ANSWER = "";
    public String TIME = "";
    public int TOTAL_ANSWER = 0;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_type__list_);

        addObjects();
        addEvents();
        loadForm();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EXAMPLE) {
            if (resultCode == Activity.RESULT_OK) {
                // get giá trị từ activity yesno question
                QUESTION_NAME += data.getStringExtra(fQUESTION) + ";";
                ANSWER += data.getStringExtra(fANSWER) + ";";
                TIME += data.getStringExtra(fTIME) + ";";
            }
        }
    }

    //region Ánh xạ đối tượng
    public void addObjects() {
        listView = (ListView) findViewById(R.id.lvQuestion);
    }
    //endregion

    //region Sự kiện xử lý
    public void addEvents() {
        loadListView();
        selectItemTypeQuestion();
    }
    //endregion

    //region Xử lý Action
    // Load form
    public void loadForm() {
        // Lấy tên khách hàng, gán giá trị cho CUS_NAME
        Intent intent = getIntent();
        CUS_NAME = intent.getStringExtra("CUS_NAME");
    }

    // Load dữ liệu lên ListView
    public void loadListView() {
        // Khởi tạo sqlite
        QuesType_DBHelper ques_Type_dbHelper = new QuesType_DBHelper(this);
        ques_Type_dbHelper.createDefaulData();

        // Lấy danh sách các record có trong bảng
        List<QuestionsType> list = ques_Type_dbHelper.getAllQuestionsType();
        this.questionsTypeList.addAll(list);

        // tạo list adapter
        this.arrayAdapter = new ArrayAdapter<QuestionsType>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.questionsTypeList);

        // đăng ký adapter cho listView
        this.listView.setAdapter(this.arrayAdapter);

        // đăng ký context menu cho listview
        registerForContextMenu(this.listView);
    }

    // Xử lý click chọn vào chi tiết loại câu hỏi
    public void selectItemTypeQuestion() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String[] prefixList = questionsTypeList.get(i).toString().split(Pattern.quote("."));
                if (prefixList[1].equals(MODE_YESNO)) {
                    Intent intent = new Intent(getApplicationContext(), YesNo_Answer_Activity.class);
                    intent.putExtra("question", questionsTypeList.get(i).toString());
                    startActivityForResult(intent, REQUEST_CODE_EXAMPLE);
                } else if (prefixList[1].equals(MODE_CK)) {
                    Intent intent = new Intent(getApplicationContext(), CheckBox_Answer_Activity.class);
                    intent.putExtra("question", questionsTypeList.get(i).toString());
                    startActivity(intent);
                } else if (prefixList[1].equals(MODE_IP)) {
                    Intent intent = new Intent(getApplicationContext(), Input_Answer_Activity.class);
                    intent.putExtra("question", questionsTypeList.get(i).toString());
                    startActivity(intent);
                } else if (prefixList[1].equals(MODE_SE)) {
                    Intent intent = new Intent(getApplicationContext(), Select_Answer_Activity.class);
                    intent.putExtra("question", questionsTypeList.get(i).toString());
                    startActivity(intent);
                }

                view.setBackgroundColor(Color.CYAN);
            }
        });
    }

    // Xử lý click finish
    public void btnFinishServey(View view) {
//        Toast.makeText(this, "Khách hàng: " + CUS_NAME + "\n"
//                + "Câu hỏi: " + QUESTION_NAME
//                + "Trả lời: " + ANSWER
//                + "Thời gian: " + TIME, Toast.LENGTH_SHORT).show();
        SaveData();
        this.onBackPressed();
    }

    // Đếm số câu hỏi đã trả lời
    public int countQuestion(String string) {
        int counter = string.split(";", -1).length - 1;
        return counter;
    }

    // Thêm dữ liệu trả lời vào database
    public void SaveData() {
        // Gán dữ liệu từ các chuỗi vào mảng.
        List<String> questionList = new ArrayList<>(Arrays.asList(QUESTION_NAME.split(";")));
        List<String> answerList = new ArrayList<>(Arrays.asList(ANSWER.split(";")));
        List<String> timeList = new ArrayList<>(Arrays.asList(TIME.split(";")));

        // Đếm số câu hỏi đã trả lời
        TOTAL_ANSWER = countQuestion(ANSWER);

        // Khởi tạo SQLite
        An_DBHelper an_dbHelper = new An_DBHelper(this);

        // Thêm giá trị vào class
        for (int i = 0; i < countQuestion(QUESTION_NAME); i++) {
            this.answer = new Answer(
                    answerList.get(i).toString()
                    , questionList.get(i).toString()
                    , CUS_NAME
                    , timeList.get(i).toString(),
                    TOTAL_ANSWER);

            // Insert xuống database
            an_dbHelper.addAnswer(answer, getApplicationContext());
        }
    }
    //endregion
}
