package com.example.hoangquocphu.demosqlite.Questions_ExpandableList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Ques_DBHelper extends SQLiteOpenHelper {

    //region Khai báo biến toàn cục
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "Questions";

    private static final String CL_ID_QUESTION = "IDQuestion";
    private static final String CL_QUESTION = "Questions";
    //endregion


    public Ques_DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //region Xử lý thêm, sửa, xoá dữ liệu

    // THÊM DỮ LIỆU VÀO BẢNG QUESTION
    public void addQuestions(Ques ques, Context context) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CL_QUESTION, ques.getQuestion());

        // Thêm 1 dòng dữ liệu
        sqLiteDatabase.insert(TABLE_NAME, null, values);

        // Thông báo
        Toast.makeText(context, "Thêm câu hỏi thành công!", Toast.LENGTH_SHORT).show();

        // Đóng kết nối
        sqLiteDatabase.close();
    }
    //endregion

    //region Xử lý search dữ liệu
    // SEARCH ALL DỮ LIỆU
    public List<Ques> getAllQuestion() {
        List<Ques> quesList = new ArrayList<Ques>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(script, null);

        // Duyệt danh sách các câu hỏi
        if (cursor.moveToFirst()) {
            do {
                Ques ques = new Ques();
                ques.setQuestion_ID(Integer.parseInt(cursor.getString(0)));
                ques.setQuestion(cursor.getString(1));

                // Thêm vào danh sách
                quesList.add(ques);
            } while (cursor.moveToNext());
        }
        return quesList;
    }
    //endregion
}
