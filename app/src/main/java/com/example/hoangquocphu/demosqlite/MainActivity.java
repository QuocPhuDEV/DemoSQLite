package com.example.hoangquocphu.demosqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hoangquocphu.demosqlite.Customer.Cus_Activity;
import com.example.hoangquocphu.demosqlite.Question.Question_List_Activity;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.itemNewServey){
            Intent intent = new Intent(MainActivity.this, Cus_Activity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
