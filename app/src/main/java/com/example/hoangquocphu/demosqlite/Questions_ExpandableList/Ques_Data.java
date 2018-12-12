package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ques_Data {
    //public final List<Ques> quesList = new ArrayList<Ques>();

    public static HashMap<String, List<String>> getData(Context context) {
        List<Ques> quesList = new ArrayList<Ques>();
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        Ques_DBHelper ques_dbHelper = new Ques_DBHelper(context);

        // Load danh sách câu hỏi
        List<Ques> list = ques_dbHelper.getAllQuestion();
        quesList.addAll(list);

        // Tạo phần tử cho list answer
        List<String> answer = new ArrayList<String>();
        answer.add("");

        for (int i = 0; i < quesList.size(); i++) {
            expandableListDetail.put(quesList.get(i).toString(), answer);
        }

        return expandableListDetail;
    }
}
