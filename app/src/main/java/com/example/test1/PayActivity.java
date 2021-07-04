package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

public class PayActivity extends AppCompatActivity {

    SQLiteDatabase db;
    DBHelper helper;
    Cursor cursor;
    Calendar cal = Calendar.getInstance();
    TextView txt1, txt2, txt3;
    ListView listView;

    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH)+1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        helper = new DBHelper(this);

        TextView textView = findViewById(R.id.textView1);
        textView.setText(year+"."+month);

        Button b1 = findViewById(R.id.btn1);
        Button b2 = findViewById(R.id.btn2);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calInput(-1);
                textView.setText(year+"."+month);
            }
        });

        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                calInput(1);
                textView.setText(year+"."+month);
            }
        });

        txt1 = findViewById(R.id.textView2);
        txt2 = findViewById(R.id.textView5);
        txt3 = findViewById(R.id.textView6);
        listView = findViewById(R.id.pay_list);

        select();
        select2();
        
        Button btn_tap1 = (Button) findViewById(R.id.tap1);
        Button btn_tap2 = (Button) findViewById(R.id.tap2);
        Button btn_tap3 = (Button) findViewById(R.id.tap3);
        Button btn_tap4 = (Button) findViewById(R.id.tap4);
        btn_tap1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btn_tap2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });
        btn_tap3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CalActivity.class);
                startActivity(intent);
            }
        });

        btn_tap4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DepartmentActivity.class);
                startActivity(intent);
            }
        });

    }
    public void calInput(int gap) {
        month += gap;
        if(month <= 0) {
            month = 12;
            year--;
        } else if(month >= 13) {
            month = 1;
            year++;
        }
    }
    public void select(){

        db = helper.getWritableDatabase();
        String sql = "select pMonthPay, pAntiPay,pExtraPay from cpaytbl where pDate = '"+year+"."+month+"'";
        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

        StringBuilder builder1 = new StringBuilder();
        StringBuilder builder2 = new StringBuilder();
        StringBuilder builder3 = new StringBuilder();
        while (cursor.moveToNext()) {
            int x = cursor.getInt(0);
            builder1.append(x).append("원");
            int y = cursor.getInt(1);
            builder2.append(y).append("원");
            int z = cursor.getInt(2);
            builder3.append(z).append("원");
        }
        txt1.setText(builder1);
        txt2.setText(builder2);
        txt3.setText(builder3);
    }
    public void select2(){
        db = helper.getWritableDatabase();
        String sql = "select wDate from cworktbl ";
        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

        PayAdapter adapter = new PayAdapter();

        while (cursor.moveToNext()){
            adapter.addItemToPay(cursor.getString(0));
        }
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
