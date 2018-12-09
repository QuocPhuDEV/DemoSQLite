package com.example.hoangquocphu.demosqlite.Question;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";


    // Phiên bản
    private static final int DATABASE_VERSION = 1;


    // Tên cơ sở dữ liệu.
    private static final String DATABASE_NAME = "DB_Customer";


    // Tên bảng: Question.
    private static final String TABLE_QUESTION = "Question";

    private static final String COLUMN_ID_QUESTION = "QuestionID";
    private static final String COLUMN_QUESTION = "question";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Script tạo bảng.
        String script = "CREATE TABLE " + TABLE_QUESTION + "("
                + COLUMN_ID_QUESTION + " INTEGER PRIMARY KEY,"
                + COLUMN_QUESTION + " TEXT" + ")";
        // Chạy lệnh tạo bảng.
        sqLiteDatabase.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Hủy (drop) bảng cũ nếu nó đã tồn tại.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);


        // Và tạo lại.
        onCreate(sqLiteDatabase);
    }

    // Nếu trong bảng Note chưa có dữ liệu,
    // Trèn vào mặc định 2 bản ghi.
    public void createDefaultQuestionIfNeed() {
        int count = this.getQuestionCount();
        if (count == 0) {
            Question ques1 = new Question("Câu hỏi trắc nghiệm");
            Question ques2 = new Question("Câu hỏi tự luận");
            Question ques3 = new Question("Câu hỏi đánh giá");
            this.addQuestion(ques1);
            this.addQuestion(ques2);
            this.addQuestion(ques3);
        }
    }

    public void addQuestion(Question question) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, question.getQuestion());


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_QUESTION, null,values);


        // Đóng kết nối database.
        db.close();
    }

    public Question getQuestion(int id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTION, new String[]{COLUMN_ID_QUESTION,
                        COLUMN_QUESTION}, COLUMN_ID_QUESTION + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Question question = new Question(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1));
        // return note
        return question;
    }

    public List<Question> getAllQuestion() {

        List<Question> quesList = new ArrayList<Question>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Question ques = new Question();

                ques.setQuestionID(Integer.parseInt(cursor.getString(0)));
                ques.setQuestion(cursor.getString(1));

                // Thêm vào danh sách.
                quesList.add(ques);
            } while (cursor.moveToNext());
        }

        // return ques list
        return quesList;
    }

    public int getQuestionCount() {

        String countQuery = "SELECT  * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    // update data
    public int updateQuestion(Question ques) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, ques.getQuestion());

        // updating row
        return db.update(TABLE_QUESTION, values, COLUMN_ID_QUESTION + " = ?",
                new String[]{String.valueOf(ques.getQuestionID())});
    }

    // xoa data
    public void deleteQuestion(Question ques) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTION, COLUMN_ID_QUESTION + " = ?",
                new String[]{String.valueOf(ques.getQuestionID())});
        db.close();
    }
}
