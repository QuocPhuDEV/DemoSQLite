package com.example.hoangquocphu.demosqlite.Customer;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Cus_DBHelper extends SQLiteOpenHelper {
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DBCustomerServey";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "Customer";

    private static final String CL_ID_CUSTOMER = "IDCustomer";
    private static final String CL_FULLNAME = "Fullname";
    private static final String CL_DATEOFBIRTH = "DateOfBirth";
    private static final String CL_EMAIL = "Email";
    private static final String CL_ADRESS = "Address";
    private static final String CL_GENDER = "Gender";
    private static final String CL_PHONE = "Phone";

    public Cus_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        dropTableAndCreate(sqLiteDatabase);
    }

    // TẠO BẢNG
    public void createTable(SQLiteDatabase sqLiteDatabase) {
        // script tạo bảng
        String script = "CREATE TABLE " + TABLE_NAME + "("
                + CL_ID_CUSTOMER + "INTEGER PRIMARY KEY, "
                + CL_FULLNAME + "TEXT, "
                + CL_DATEOFBIRTH + "TEXT, "
                + CL_GENDER + "INTEGER, "
                + CL_EMAIL + "TEXT, "
                + CL_ADRESS + "TEXT, "
                + CL_PHONE + "TEXT" + ")";

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
    public void addCustomer(Customer customer) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CL_FULLNAME, customer.getFullName());
        values.put(CL_DATEOFBIRTH, customer.getDateOfBirth());
        values.put(CL_GENDER, customer.getGender());
        values.put(CL_EMAIL, customer.getEmail());
        values.put(CL_ADRESS, customer.getAddress());
        values.put(CL_PHONE, customer.getPhoneNumber());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME, null, values);

        // đóng kết nối
        database.close();
    }

    // SEACH ALL DỮ LIỆU
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<Customer>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);

        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();

                customer.setCusID(Integer.parseInt(cursor.getString(0)));
                customer.setFullName(cursor.getString(1));
                customer.setDateOfBirth(cursor.getString(2));
                customer.setEmail(cursor.getString(3));
                customer.setAddress(cursor.getString(4));
                customer.setPhoneNumber(cursor.getString(5));
                customer.setGender(Integer.parseInt(cursor.getString(6)));

                // Thêm vào danh sách
                customerList.add(customer);

            } while (cursor.moveToNext());
        }
        return customerList;
    }


}
