package com.example.hoangquocphu.demosqlite.Customer;

        import android.app.DatePickerDialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.DatePicker;
        import android.widget.TextView;

        import com.example.hoangquocphu.demosqlite.Question.Question_List_Activity;
        import com.example.hoangquocphu.demosqlite.R;

        import java.util.Calendar;

public class Cus_Activity extends AppCompatActivity {
    private TextView DateOfBirth;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cus_);
        addObjects();
        addEvents();
    }



    // Ánh xạ đối tượng
    public void addObjects() {
        DateOfBirth = (TextView) findViewById(R.id.tvDatofbirth);
    }

    // Thêm sự kiện xử lý
    public void addEvents() {
        setDateOfBirth();
    }

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

    // Sử kiện xử lý button Next
    public void btnNext(View view) {
        Intent intent = new Intent(this, Question_List_Activity.class);
        startActivity(intent);
    }
}
