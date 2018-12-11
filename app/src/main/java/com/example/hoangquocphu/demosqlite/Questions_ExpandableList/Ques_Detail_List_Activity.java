package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.Questions.Ques_DBHelper;
import com.example.hoangquocphu.demosqlite.Questions.Questions;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ques_Detail_List_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ques__detail__list_);

        addObejct();
        addEvents();

    }

    //region Ánh xạ đối tượng
    public void addObejct() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandListView);
        expandableListDetail = Ques_Data.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new Custom_Adapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        
    }
    //endregion

    //region Description
    public void addEvents() {

    }
    //endregion
}
