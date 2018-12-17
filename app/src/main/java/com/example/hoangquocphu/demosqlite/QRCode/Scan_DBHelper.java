package com.example.hoangquocphu.demosqlite.QRCode;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Scan_DBHelper extends SQLiteOpenHelper {
    //region Khai báo biến toàn cục
    // Phiên bản SQLite
    private static final int DATABASE_VERSION = 1;

    // Tên CSDL
    private static final String DATABASE_NAME = "DB_Customer";

    // Tên bảng, các cột
    private static final String TABLE_NAME = "Scan";

    private static final String CL_ID_SCAN = "IdScan";
    private static final String CL_MAHANG = "MaHang";
    private static final String CL_SOID = "SoID";
    private static final String CL_SCAN_TIME = "ScanTime";
    //endregion

    public Scan_DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // THÊM DỮ LIỆU VÀO BẢNG SCAN
    public void addScan(Scan scan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(CL_MAHANG, scan.getMaHang());
        values.put(CL_SOID, scan.getSoID());
        values.put(CL_SCAN_TIME, scan.getScanTime());

        // thêm 1 dòng dữ liệu vào bảng
        database.insert(TABLE_NAME, null, values);

        // đóng kết nối
        database.close();
    }

    // SEARCH ALL DỮ LIỆU
    public List<Scan> getAllScanData() {
        List<Scan> scanList = new ArrayList<Scan>();

        // script search all
        String script = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(script, null);

        // Duyệt danh sách search được
        if (cursor.moveToFirst()) {
            do {
                Scan scan = new Scan();

                scan.setIdScan(Integer.parseInt(cursor.getString(0)));
                scan.setMaHang(cursor.getString(1));
                scan.setSoID(cursor.getString(2));
                scan.setScanTime(cursor.getString(3));

                // Thêm vào danh sách
                scanList.add(scan);
            } while (cursor.moveToNext());
        }
        return scanList;
    }

    // ĐẾM DỮ LIỆU THEO ĐIỀU KIỆN
    public int getCountScan(String maHang, String soID) {
        String countQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE MaHang = '" + maHang + "' and SoID = '" + soID + "' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }

    // XÓA HẾT DỮ LIỆU SCAN

    public void deleteAllData() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, null, null);
        database.close();
    }
}
