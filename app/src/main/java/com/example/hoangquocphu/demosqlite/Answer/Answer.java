package com.example.hoangquocphu.demosqlite.Answer;

import java.io.Serializable;

public class Answer implements Serializable {
    private int IdAnswer;
    private String An_Answer;
    private String An_Question;
    private String An_Customer;
    private String An_AnswerTime;
    private int An_TotalAnswer;

    public Answer() {
    }

    public Answer(int idAnswer, String an_Answer, String an_Question, String an_Customer, String an_AnswerTime, int an_TotalAnswer) {
        IdAnswer = idAnswer;
        An_Answer = an_Answer;
        An_Question = an_Question;
        An_Customer = an_Customer;
        An_AnswerTime = an_AnswerTime;
        An_TotalAnswer = an_TotalAnswer;
    }

    public Answer(String an_Answer, String an_Question, String an_Customer, String an_AnswerTime, int an_TotalAnswer) {
        An_Answer = an_Answer;
        An_Question = an_Question;
        An_Customer = an_Customer;
        An_AnswerTime = an_AnswerTime;
        An_TotalAnswer = an_TotalAnswer;
    }

    public int getIdAnswer() {
        return IdAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        IdAnswer = idAnswer;
    }

    public String getAn_Answer() {
        return An_Answer;
    }

    public void setAn_Answer(String an_Answer) {
        An_Answer = an_Answer;
    }

    public String getAn_Question() {
        return An_Question;
    }

    public void setAn_Question(String an_Question) {
        An_Question = an_Question;
    }

    public String getAn_Customer() {
        return An_Customer;
    }

    public void setAn_Customer(String an_Customer) {
        An_Customer = an_Customer;
    }

    public String getAn_AnswerTime() {
        return An_AnswerTime;
    }

    public void setAn_AnswerTime(String an_AnswerTime) {
        An_AnswerTime = an_AnswerTime;
    }

    public int getAn_TotalAnswer() {
        return An_TotalAnswer;
    }

    public void setAn_TotalAnswer(int an_TotalAnswer) {
        An_TotalAnswer = an_TotalAnswer;
    }

    @Override
    public String toString() {
        return An_Customer + An_AnswerTime;
    }
}
