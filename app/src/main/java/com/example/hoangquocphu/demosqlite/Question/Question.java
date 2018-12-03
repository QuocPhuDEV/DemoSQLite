package com.example.hoangquocphu.demosqlite.Question;

import java.io.Serializable;

public class Question implements Serializable {
    private int QuestionID;
    private String Question;

    public  Question(){

    }
    public Question(int questionID, String question) {
        QuestionID = questionID;
        Question = question;
    }

    public Question(String question) {
        Question = question;
    }

    public int getQuestionID() {
        return QuestionID;
    }

    public void setQuestionID(int questionID) {
        QuestionID = questionID;
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
