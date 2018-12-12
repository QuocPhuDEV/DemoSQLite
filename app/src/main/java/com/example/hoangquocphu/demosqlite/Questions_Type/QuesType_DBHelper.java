package com.example.hoangquocphu.demosqlite.Questions_Type;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hoangquocphu.demosqlite.Questions_ExpandableList.Ques;

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

    // THÊM DỮ LIỆU MẪU
    public void createDefaulData() {
        int count = this.getQuestionCount();
        if (count == 0) {
            QuestionsType questionsType1 = new QuestionsType("1.(YN). Bạn có biết về công ty ABC không? ");
            QuestionsType questionsType2 = new QuestionsType("2.(YN). Bạn đã từng mua sản phẩm nào từ công ty ABC chưa? ");
            QuestionsType questionsType3 = new QuestionsType("3.(OP). Bạn đã mua những sản phẩm gì? ");
            QuestionsType questionsType4 = new QuestionsType("4.(SE). Bạn mua nó ở đâu? ");
            QuestionsType questionsType5 = new QuestionsType("5.(IP). Bạn đã mua bao nhiêu lần? ");
            QuestionsType questionsType6 = new QuestionsType("6.(IP). Bạn thấy chất lượng sản phẩm như thế nào? ");
            QuestionsType questionsType7 = new QuestionsType("7.(YN). Bạn có ý định mua thêm sản phẩm? ");
            QuestionsType questionsType8 = new QuestionsType("8.(YN). Bạn đã từng giới thiệu với ai về sản phẩm? ");
            QuestionsType questionsType9 = new QuestionsType("9.(YN). Bạn sẽ tiếp tục ủng hộ sản phẩm của ABC ? ");
            QuestionsType questionsType10 = new QuestionsType("10.(IP). Bạn có muốn góp ý gì khác không? ");

            this.addQuestions(questionsType1);
            this.addQuestions(questionsType2);
            this.addQuestions(questionsType3);
            this.addQuestions(questionsType4);
            this.addQuestions(questionsType5);
            this.addQuestions(questionsType6);
            this.addQuestions(questionsType7);
            this.addQuestions(questionsType8);
            this.addQuestions(questionsType9);
            this.addQuestions(questionsType10);
        }

    }

    // KIỂM TRA TÌNH TRẠNG DỮ LIỆU
    public int getQuestionCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
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
