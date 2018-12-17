package com.example.hoangquocphu.demosqlite.Answer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class An_DBHelper extends SQLiteOpenHelper {
    //region Khai báo biến toàn cục
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "Answer";

    private static final String CL_ID_ANSWER = "IdAnswer";
    private static final String CL_ANSWER = "An_Answer";
    private static final String CL_ANSWER_QUESTION = "An_Question";
    private static final String CL_ANSWER_CUSTOMER = "An_Customer";
    private static final String CL_ANSWER_TIME = "An_AnswerTime";
    private static final String CL_ANSWER_TOTAL = "An_TotalAnswer";
    //endregion

    public An_DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // THÊM DỮ LIỆU VÀO BẢNG CUSTOMER
    public void addAnswer(Answer answer, Context context) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CL_ANSWER, answer.getAn_Answer());
        values.put(CL_ANSWER_QUESTION, answer.getAn_Question());
        values.put(CL_ANSWER_CUSTOMER, answer.getAn_Customer());
        values.put(CL_ANSWER_TIME, answer.getAn_AnswerTime());
        values.put(CL_ANSWER_TOTAL, answer.getAn_TotalAnswer());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME, null, values);

        // Thông báo
        Toast.makeText(context, "Lưu khảo sát thành công!", Toast.LENGTH_SHORT).show();
        // đóng kết nối
        database.close();
    }

    // SEARCH ALL DỮ LIỆU
    public List<Answer> getAllAnswer() {
        List<Answer> answerList = new ArrayList<Answer>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);

        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                Answer answer = new Answer();

                answer.setIdAnswer(Integer.parseInt(cursor.getString(0)));
                answer.setAn_Answer(cursor.getString(1));
                answer.setAn_Question(cursor.getString(2));
                answer.setAn_Customer(cursor.getString(3));
                answer.setAn_AnswerTime(cursor.getString(4));
                answer.setAn_TotalAnswer(Integer.parseInt(cursor.getString(5)));

                // Thêm vào danh sách
                answerList.add(answer);
            } while (cursor.moveToNext());
        }
        return answerList;
    }

    // THÊM DỮ LIỆU MẶC ĐỊNH CHO BẢNG
    public void createDefaultHistory(Context context) {
        int count = this.getHistoryCount();
        if (count == 0) {
            Answer answer = new Answer("mr.phu", "your name?", "phu", "11:12:00", 10);
            this.addAnswer(answer, context);
        }

    }

    // ĐẾM RECORD TRONG DATABASE
    public int getHistoryCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
}
