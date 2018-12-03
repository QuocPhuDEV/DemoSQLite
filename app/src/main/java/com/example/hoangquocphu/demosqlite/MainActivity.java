package com.example.hoangquocphu.demosqlite;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.Question.AddQuestion;
import com.example.hoangquocphu.demosqlite.Question.DBHelper;
import com.example.hoangquocphu.demosqlite.Question.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;


    private static final int MY_REQUEST_CODE = 1000;

    private final List<Question> questionList = new ArrayList<Question>();
    private ArrayAdapter<Question> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.listView);

        DBHelper db = new DBHelper(this);
        db.createDefaultQuestionIfNeed();

        List<Question> list = db.getAllQuestion();
        this.questionList.addAll(list);

        this.listViewAdapter = new ArrayAdapter<Question>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.questionList);

        // Đăng ký Adapter cho ListView.
        this.listView.setAdapter(this.listViewAdapter);

        // Đăng ký Context menu cho ListView.
        registerForContextMenu(this.listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW, 0, "View Question");
        menu.add(0, MENU_ITEM_CREATE, 1, "Create Question");
        menu.add(0, MENU_ITEM_EDIT, 2, "Edit Question");
        menu.add(0, MENU_ITEM_DELETE, 3, "Delete Question");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Question selectQuestion = (Question) this.listView.getItemAtPosition(info.position);

        if (item.getItemId() == MENU_ITEM_VIEW) {
            Toast.makeText(getApplicationContext(), selectQuestion.getQuestion(), Toast.LENGTH_LONG).show();

        } else if (item.getItemId() == MENU_ITEM_CREATE) {
            Intent intent = new Intent(this, AddQuestion.class);
            this.startActivityForResult(intent, MY_REQUEST_CODE);

        } else if (item.getItemId() == MENU_ITEM_EDIT) {
            Intent intent = new Intent(this, AddQuestion.class);
            intent.putExtra("question", selectQuestion);
            this.startActivityForResult(intent, MY_REQUEST_CODE);

        } else if (item.getItemId() == MENU_ITEM_DELETE) {
            // Hỏi trước khi xóa.
            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteQuestion(selectQuestion);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            return false;
        }
        return true;
    }


    // Người dùng đồng ý xóa
    private void deleteQuestion(Question ques) {
        DBHelper db = new DBHelper(this);
        db.deleteQuestion((ques));
        this.questionList.remove((ques));
        // Refresh ListView.
        this.listViewAdapter.notifyDataSetChanged();
    }

    // Khi AddQuestion hoàn thành, nó gửi phản hồi lại.
    // (Nếu bạn đã start nó bằng cách sử dụng startActivityForResult()  )
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                this.questionList.clear();
                DBHelper db = new DBHelper(this);
                List<Question> list = db.getAllQuestion();
                this.questionList.addAll(list);
                // Thông báo dữ liệu thay đổi (Để refresh ListView).
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

}
