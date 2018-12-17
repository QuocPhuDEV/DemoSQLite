package com.example.hoangquocphu.demosqlite.Customer;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cus_DBHelper extends SQLiteOpenHelper {
    //region Khai báo biến toàn cục
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng
    private static final String TABLE_NAME_CUSTOMER = "Customer";
    private static final String TABLE_NAME_QUESTION_TYPE = "QuestionsType";
    private static final String TABLE_NAME_QUESTION = "Questions";
    private static final String TABLE_NAME_ANSWER = "Answer";

    // các cột
    // BẢNG CUSTOMER
    private static final String CL_ID_CUSTOMER = "IDCustomer";
    private static final String CL_FULLNAME = "Fullname";
    private static final String CL_DATEOFBIRTH = "DateOfBirth";
    private static final String CL_EMAIL = "Email";
    private static final String CL_ADRESS = "Address";
    private static final String CL_GENDER = "Gender";
    private static final String CL_PHONE = "Phone";

    // BẢNG QUESTION TYPE
    private static final String CL_ID_QUESTION_TYPE = "IDQuestionType";
    private static final String CL_QUESTION_TYPE = "QuestionsType";

    // BẢNG QUESTION
    private static final String CL_ID_QUESTION = "IDQuestion";
    private static final String CL_QUESTION = "Questions";

    // BẢNG ANSWER
    private static final String CL_ID_ANSWER = "IdAnswer";
    private static final String CL_ANSWER = "An_Answer";
    private static final String CL_ANSWER_QUESTION = "An_Question";
    private static final String CL_ANSWER_CUSTOMER = "An_Customer";
    private static final String CL_ANSWER_TIME = "An_AnswerTime";
    private static final String CL_ANSWER_TOTAL = "An_TotalAnswer";
    //endregion

    public Cus_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTableCustomer(sqLiteDatabase);
        createTableQuestionType(sqLiteDatabase);
        createTableQuestion(sqLiteDatabase);
        createTableAnswer(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTableAndCreateCustomer(sqLiteDatabase);
        dropTableAndCreateQuestionType(sqLiteDatabase);
        dropTableAndCreateQuestion(sqLiteDatabase);
        dropTableAndCreateAnswer(sqLiteDatabase);
    }

    //region Tạo bảng trong database
    // TẠO BẢNG CUSTOMER
    public void createTableCustomer(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME_CUSTOMER + "("
                + CL_ID_CUSTOMER + " INTEGER PRIMARY KEY, "
                + CL_FULLNAME + " TEXT, "
                + CL_DATEOFBIRTH + " TEXT, "
                + CL_GENDER + " TEXT, "
                + CL_EMAIL + " TEXT, "
                + CL_PHONE + " TEXT, "
                + CL_ADRESS + " TEXT " + ")";

        // chạy lệnh tạo bảng
        sqLiteDatabase.execSQL(script);
    }

    // TẠO BẢNG QUESTION TYPE
    public void createTableQuestionType(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME_QUESTION_TYPE + "("
                + CL_ID_QUESTION_TYPE + " INTEGER PRIMARY KEY, "
                + CL_QUESTION_TYPE + " TEXT " + ")";

        // chạy lệnh tạo bảng
        sqLiteDatabase.execSQL(script);
    }

    // TẠO BẢNG QUESTION
    public void createTableQuestion(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME_QUESTION + "("
                + CL_ID_QUESTION + " INTEGER PRIMARY KEY, "
                + CL_QUESTION + " TEXT " + ")";

        // chạy lệnh tạo bảng
        sqLiteDatabase.execSQL(script);

    }

    // TẠO BẢNG ANSWER
    public void createTableAnswer(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME_ANSWER + " ("
                + CL_ID_ANSWER + " INTEGER PRIMARY KEY, "
                + CL_ANSWER + " TEXT, "
                + CL_ANSWER_QUESTION + " TEXT, "
                + CL_ANSWER_CUSTOMER + " TEXT, "
                + CL_ANSWER_TIME + " TEXT, "
                + CL_ANSWER_TOTAL + " TEXT " + " )";

        // chạy lệnh tạo bảng
        sqLiteDatabase.execSQL(script);
    }
    //endregion

    //region Xoá bảng trong database
    // XÓA BẢNG CUSTOMER NẾU TỒN TẠI, TẠO LẠI
    public void dropTableAndCreateCustomer(SQLiteDatabase sqLiteDatabase) {
        //script xóa bảng
        String script = "DROP TABLE IF EXISTS " + TABLE_NAME_CUSTOMER;

        // Xóa bảng
        sqLiteDatabase.execSQL(script);

        // tạo lại bảng
        onCreate(sqLiteDatabase);
    }

    // XÓA BẢNG QUESTION TYPE NẾU TỒN TẠI, TẠO LẠI
    public void dropTableAndCreateQuestionType(SQLiteDatabase sqLiteDatabase) {
        //script xóa bảng
        String script = "DROP TABLE IF EXISTS " + TABLE_NAME_QUESTION_TYPE;

        // Xóa bảng
        sqLiteDatabase.execSQL(script);

        // tạo lại bảng
        onCreate(sqLiteDatabase);
    }

    // XÓA BẢNG QUESTION NẾU TỒN TẠI, TẠO LẠI
    public void dropTableAndCreateQuestion(SQLiteDatabase sqLiteDatabase) {
        //script xóa bảng
        String script = "DROP TABLE IF EXISTS " + TABLE_NAME_QUESTION;

        // Xóa bảng
        sqLiteDatabase.execSQL(script);

        // tạo lại bảng
        onCreate(sqLiteDatabase);
    }

    // XOÁ BẢNG ANSWER NẾU TỒN TẠI, TẠO LẠI
    public void dropTableAndCreateAnswer(SQLiteDatabase sqLiteDatabase) {
        //script xóa bảng
        String script = "DROP TABLE IF EXISTS " + TABLE_NAME_ANSWER;

        // Xóa bảng
        sqLiteDatabase.execSQL(script);

        // tạo lại bảng
        onCreate(sqLiteDatabase);
    }
    //endregion

    //region Thêm dữ liệu mặc định cho bảng
    // THÊM DỮ LIỆU VÀO BẢNG CUSTOMER
    public void addCustomer(Customer customer, Context context) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CL_FULLNAME, customer.getFullName());
        values.put(CL_DATEOFBIRTH, customer.getDateOfBirth());
        values.put(CL_GENDER, customer.getGender());
        values.put(CL_EMAIL, customer.getEmail());
        values.put(CL_PHONE, customer.getPhoneNumber());
        values.put(CL_ADRESS, customer.getAddress());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME_CUSTOMER, null, values);

        // Thông báo
        Toast.makeText(context, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();

        // đóng kết nối
        database.close();
    }

    // THÊM DỮ LIỆU MẶC ĐỊNH CHO CUSTOMER
    public void createDefaultCustomer(Context context) {

        int count = this.getCustomerCount();
        if (count == 0) {
            Customer customer = new Customer("Phan Văn Đức", null, "Male", null, null, "Nghệ An");
            Customer customer1 = new Customer("Nguyễn Công Phượng", null, "Male", null, null, "Nghệ An");
            Customer customer2 = new Customer("Nguyễn Quang Hải", null, "Male", null, null, "Hà Nội");
            Customer customer3 = new Customer("Nguyễn Trọng Hoàng", null, "Male", null, null, "Nghệ An");
            Customer customer4 = new Customer("Đỗ Hùng Dũng", null, "Male", null, null, "Hà Nội");
            Customer customer5 = new Customer("Nguyễn Anh Đức", null, "Male", null, null, "Bình Dương");
            Customer customer6 = new Customer("Nguyễn Đình Trọng", null, "Male", null, null, "Móng Cái");
            Customer customer7 = new Customer("Đoàn Văn Hậu", null, "Male", null, null, "Cao Bằng");
            Customer customer8 = new Customer("Quế Ngọc Hải (C)", null, "Male", null, null, "Nghệ An");
            Customer customer9 = new Customer("Lương Xuân Trường", null, "Male", null, null, "Hải Dương");
            Customer customer10 = new Customer("Bùi Tiến Dũng", null, "Male", null, null, "Thanh Hoá");

            this.addCustomer(customer, context);
            this.addCustomer(customer1, context);
            this.addCustomer(customer2, context);
            this.addCustomer(customer3, context);
            this.addCustomer(customer4, context);
            this.addCustomer(customer5, context);
            this.addCustomer(customer6, context);
            this.addCustomer(customer7, context);
            this.addCustomer(customer8, context);
            this.addCustomer(customer9, context);
            this.addCustomer(customer10, context);
        }

    }
    //endregion

    //region Search dữ liệu
    // KIỂM TRA TÌNH TRẠNG DỮ LIỆU
    public int getCustomerCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME_CUSTOMER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    // SEACH ALL DỮ LIỆU
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<Customer>();
        // script search all
        String script = "SELECT * FROM " + TABLE_NAME_CUSTOMER;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);
        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setCusID(Integer.parseInt(cursor.getString(0)));
                customer.setFullName(cursor.getString(1));
                customer.setDateOfBirth(cursor.getString(2));
                customer.setGender(cursor.getString(3));
                customer.setEmail(cursor.getString(4));
                customer.setPhoneNumber(cursor.getString(5));
                customer.setAddress(cursor.getString(6));

                // Thêm vào danh sách
                customerList.add(customer);

            } while (cursor.moveToNext());
        }
        return customerList;
    }
    //endregion

}
