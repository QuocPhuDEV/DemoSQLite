package com.example.hoangquocphu.demosqlite.Questions_Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class QuesType_DBHelper extends SQLiteOpenHelper {
    //region Khai báo biến toàn cục
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "QuestionsType";

    private static final String CL_ID_QUESTION_TYPE = "IDQuestionType";
    private static final String CL_QUESTION_TYPE = "QuestionsType";
    //endregion

    public QuesType_DBHelper(Context context) {
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

//    // TẠO BẢNG
//    public void createTable(SQLiteDatabase sqLiteDatabase) {
//        // script tạo bảng
//        String script = "CREATE TABLE " + TABLE_NAME + "("
//                + CL_ID_QUESTION_TYPE + " INTEGER PRIMARY KEY, "
//                + CL_QUESTION_TYPE + " TEXT " + ")";
//
//        // chạy lệnh tạo bảng
//        sqLiteDatabase.execSQL(script);
//    }
//
//    // XÓA BẢNG NẾU TỒN TẠI, TẠO LẠI
//    public void dropTableAndCreate(SQLiteDatabase sqLiteDatabase) {
//        //script xóa bảng
//        String script = "DROP TABLE IF EXISTS " + TABLE_NAME;
//
//        // Xóa bảng
//        sqLiteDatabase.execSQL(script);
//
//        // tạo lại bảng
//        onCreate(sqLiteDatabase);
//    }

    // THÊM DỮ LIỆU VÀO BẢNG CUSTOMER
    public void addQuestions(QuestionsType questionsType) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CL_QUESTION_TYPE, questionsType.getQuestion());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME, null, values);

        // đóng kết nối
        database.close();
    }

    // SEACH ALL DỮ LIỆU
    public List<QuestionsType> getAllQuestionsType() {
        List<QuestionsType> questionsTypeList = new ArrayList<QuestionsType>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);

        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                QuestionsType questionsType = new QuestionsType();

                questionsType.setQuestion_ID(Integer.parseInt(cursor.getString(0)));
                questionsType.setQuestion(cursor.getString(1));

                // Thêm vào danh sách
                questionsTypeList.add(questionsType);

            } while (cursor.moveToNext());
        }
        return questionsTypeList;
    }
}
