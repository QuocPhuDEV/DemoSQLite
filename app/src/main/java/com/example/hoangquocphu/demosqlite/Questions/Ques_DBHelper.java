package com.example.hoangquocphu.demosqlite.Questions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoangquocphu.demosqlite.Customer.Customer;

import java.util.ArrayList;
import java.util.List;

public class Ques_DBHelper extends SQLiteOpenHelper {
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "Questions";

    private static final String CL_ID_QUESTION = "IDQuestion";
    private static final String CL_QUESTION = "Questions";

    public Ques_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //dropTableAndCreate(sqLiteDatabase);
    }

    // TẠO BẢNG
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + CL_ID_QUESTION + " INTEGER PRIMARY KEY, "
                + CL_QUESTION + " TEXT " + ")";

        // chạy lệnh tạo bảng
        sqLiteDatabase.execSQL(script);
    }

    // XÓA BẢNG NẾU TỒN TẠI, TẠO LẠI
    public void dropTableAndCreate(SQLiteDatabase sqLiteDatabase) {
        //script xóa bảng
        String script = "DROP TABLE IF EXISTS " + TABLE_NAME;

        // Xóa bảng
        sqLiteDatabase.execSQL(script);

        // tạo lại bảng
        onCreate(sqLiteDatabase);
    }

    // THÊM DỮ LIỆU VÀO BẢNG CUSTOMER
    public void addQuestions(Questions questions) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CL_QUESTION, questions.getQuestion());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME, null, values);

        // đóng kết nối
        database.close();
    }

    // SEACH ALL DỮ LIỆU
    public List<Questions> getAllQuestions() {
        List<Questions> questionsList = new ArrayList<Questions>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);

        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                Questions questions = new Questions();

                questions.setQuestion_ID(Integer.parseInt(cursor.getString(0)));
                questions.setQuestion(cursor.getString(1));


                // Thêm vào danh sách
                questionsList.add(questions);

            } while (cursor.moveToNext());
        }
        return questionsList;
    }
}
