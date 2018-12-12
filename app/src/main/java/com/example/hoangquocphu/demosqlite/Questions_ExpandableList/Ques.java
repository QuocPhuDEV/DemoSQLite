package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import java.io.Serializable;

public class Ques implements Serializable {
    private int Question_ID;
    private String Question;

    public Ques() {
    }

    public Ques(String question) {
        Question = question;
    }

    public int getQuestion_ID() {
        return Question_ID;
    }

    public void setQuestion_ID(int question_ID) {
        Question_ID = question_ID;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    @Override
    public String toString() {
        return this.Question;
    }
}
