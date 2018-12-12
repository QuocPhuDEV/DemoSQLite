package com.example.hoangquocphu.demosqlite.Customer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoangquocphu.demosqlite.Questions_Type.QuesType_List_Activity;
import com.example.hoangquocphu.demosqlite.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Cus_Activity extends AppCompatActivity {
    //region Khai báo biến toàn cục
    private EditText edFullName, edMail, edPhone;
    private Spinner spZone;
    private RadioButton rdoMale, rdoFemale;
    private TextView DateOfBirth;

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private static final int MODE_CREATE = 1;
    private int mode;
    private boolean needRefresh;
    private Customer customer;
    //endregion


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_);
        addObjects();
        addEvents();
    }


    //region Ánh xạ đối tượng
    public void addObjects() {
        edFullName = (EditText) findViewById(R.id.edFullName);
        DateOfBirth = (TextView) findViewById(R.id.tvDatofbirth);
        rdoMale = (RadioButton) findViewById(R.id.rdoMale);
        rdoFemale = (RadioButton) findViewById(R.id.rdoFemale);
        edMail = (EditText) findViewById(R.id.edEmail);
        edPhone = (EditText) findViewById(R.id.edPhone);
        spZone = (Spinner) findViewById(R.id.spZone);
    }
    //endregion

    //region Thêm sự kiện xử lý
    public void addEvents() {
        setDateOfBirth();
        spZone();
    }
    //endregion

    //region Khởi tạo control trên form

    // Chọn ngày sinh
    public void setDateOfBirth() {
        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Cus_Activity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        onDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = day + "/" + month + "/" + year;
                DateOfBirth.setText(date);
            }
        };
    }

    // Xử lý radioButton
    public void onRadioButtonClicked(View view) {
        // Kiểm tra button được check
        boolean checked = ((RadioButton) view).isChecked();

        // Xử lý khi check radion button
        switch (view.getId()) {
            case R.id.rdoMale:
                if (checked)
                    Toast.makeText(this, "Male", Toast.LENGTH_SHORT).show();
                rdoFemale.setChecked(false);
                break;
            case R.id.rdoFemale:
                if (checked)
                    Toast.makeText(this, "Female", Toast.LENGTH_SHORT).show();
                rdoMale.setChecked(false);
                break;
        }
    }

    // Xử lý tự kiện spinner Zone
    public void spZone() {
        List<String> list = new ArrayList<>();
        list.add("TP.Hồ Chí Minh");
        list.add("Hà Nội");
        list.add("Nghệ An");
        list.add("An Giang");
        list.add("Đồng Nai");
        list.add("Quảng Bình");

        // thêm danh sách dữ liệu vào adapter
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        // Gán giá trị cho spinner
        spZone.setAdapter(adapter);

        // xử lý sự kiện chọn item
        spZone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //endregion

    //region Xử lý Button

    // Sử kiện xử lý button Next
    public void btnNext(View view) {
        SaveCustomerInformation();

        Intent intent = new Intent(Cus_Activity.this, Cus_List_Activity.class);
        startActivity(intent);
    }

    //endregion

    //region Xử lý action
    public void SaveCustomerInformation() {
        // Khởi tạo SQLite
        Cus_DBHelper cus_dbHelper = new Cus_DBHelper(this);

        // Khai báo biến nhận giá trị trừ control
        String fullName = this.edFullName.getText().toString();
        String dateOfBirth = this.DateOfBirth.getText().toString();
        String mail = this.edMail.getText().toString();
        String phone = this.edPhone.getText().toString();
        String address = this.spZone.getSelectedItem().toString();
        String gender = "";
        if (rdoMale.isChecked()) {
            gender = "Male";
        } else {
            gender = "Female";
        }

        // Thêm giá trị vào class
        this.customer = new Customer(fullName, dateOfBirth, gender, mail, phone, address);

        // Thực hiện insert
        cus_dbHelper.addCustomer(customer, getApplicationContext());


//        this.needRefresh = true;
//        this.onBackPressed();
    }
    //endregion


}
