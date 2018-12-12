package com.example.hoangquocphu.demosqlite.Questions_Type;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.Questions_ExpandableList.Ques_Detail_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class QuesType_List_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private ListView listView;

    private final List<QuestionsType> questionsTypeList = new ArrayList<QuestionsType>();
    private ArrayAdapter<QuestionsType> arrayAdapter;

    private final String MODE_YESNO = "(YN)";
    private final String MODE_OP = "(OP)";
    private final String MODE_IP = "(IP)";
    private final String MODE_SE = "(SE)";
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques_type__list_);

        addObjects();
        addEvents();
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

    //region xử lý Action

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
                    Intent intent = new Intent(getApplicationContext(), Ques_Detail_List_Activity.class);
                    startActivity(intent);
                }
            }
        });
    }
    //endregion
}
