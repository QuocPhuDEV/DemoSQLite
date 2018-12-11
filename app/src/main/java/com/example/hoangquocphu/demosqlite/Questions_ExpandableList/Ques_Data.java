package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ques_Data {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("India");
//        cricket.add("Pakistan");
//        cricket.add("Australia");
//        cricket.add("England");
//        cricket.add("South Africa");
//
        List<String> football = new ArrayList<String>();
        football.add("Brazil");
//        football.add("Spain");
//        football.add("Germany");
//        football.add("Netherlands");
//        football.add("Italy");
//
        List<String> basketball = new ArrayList<String>();
        basketball.add("United States");
//        basketball.add("Spain");
//        basketball.add("Argentina");
//        basketball.add("France");
//        basketball.add("Russia");

        expandableListDetail.put("Bạn có thích đội tuyển Việt Nam không ?", cricket);
        expandableListDetail.put("Bạn có nghĩ Việt Nam sẽ vô địch AFF 2018 ?", football);
        expandableListDetail.put("Bạn đã mua được vé trận chung kết chưa ?", basketball);
        return expandableListDetail;
    }
}
