package com.example.test1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.databinding.ActivityCalBinding;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.R.layout.activity_list_item;

public class CalActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityCalBinding binding;

    TextView textView;
    CalendarView calendarView;

    ListView listView;

    String dbname = "commute.db";

    String x;

    String sql;

    SQLiteDatabase db;
    DBHelper helper;
    CalAdapter adapter;
    Cursor cursor;
    Calendar cal = Calendar.getInstance();

    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH)+1;
    int day = cal.get(Calendar.DAY_OF_MONTH);

    private ArrayList<DailyList> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal);

        binding = ActivityCalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalActivity.this, CalEditActivity.class);
                startActivity(intent);
            }
        });
        helper = new DBHelper(this);
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

        textView = findViewById(R.id.textView);
        calendarView = findViewById(R.id.calendarView);

        textView.setText(year+"년 "+month+"월 "+day+"일");

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                textView.setText(String.format("%d년 %d월 %d일", year,month+1,dayOfMonth));
                textView.setVisibility(View.VISIBLE);

                if(month<9){
                    if(dayOfMonth<10){
                        String x = year+"-"+0+(month+1)+"-"+0+dayOfMonth;
                        db = helper.getWritableDatabase();
                        sql = "select sText,sStartTime, sEndTime from cscheduletbl where '"+x+"'>= sStartDate AND '"+x+"' <= sEndDate order by sStartTime ";
                        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                        CalAdapter adapter = new CalAdapter();

                        while (cursor.moveToNext()){
                            adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        String x = year+"-"+0+(month+1)+"-"+dayOfMonth;
                        db = helper.getWritableDatabase();
                        sql = "select sText,sStartTime, sEndTime from cscheduletbl where '"+x+"'>= sStartDate AND '"+x+"' <= sEndDate order by sStartTime ";
                        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                        CalAdapter adapter = new CalAdapter();

                        while (cursor.moveToNext()){
                            adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }else{
                    if(dayOfMonth<10){
                        String x = year+"-"+(month+1)+"-"+0+dayOfMonth;
                        db = helper.getWritableDatabase();
                        sql = "select sText,sStartTime, sEndTime from cscheduletbl where '"+x+"'>= sStartDate AND '"+x+"' <= sEndDate order by sStartTime ";
                        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                        CalAdapter adapter = new CalAdapter();

                        while (cursor.moveToNext()){
                            adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }else{
                        String x = year+"-"+(month+1)+"-"+dayOfMonth;
                        db = helper.getWritableDatabase();
                        sql = "select sText,sStartTime, sEndTime from cscheduletbl where '"+x+"'>= sStartDate AND '"+x+"' <= sEndDate order by sStartTime ";
                        cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                        CalAdapter adapter = new CalAdapter();

                        while (cursor.moveToNext()){
                            adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                        }
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }

            }
        });

        data = new ArrayList<>();

        listView = findViewById(R.id.cal_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                db = helper.getWritableDatabase();
                String sql = "select sStartDate, sEndDate, sStartTime, sEndTime, sText from cscheduletbl order by sNum DESC limit 1;"; //where에 암것두 안쓰면 마지막에 저장한 값으로 나옴
                cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

                String a = "";
                String b = "";
                String c = "";
                String d = "";
                String e = "";

                while (cursor.moveToNext()) {
                    a = cursor.getString(0);
                    b = cursor.getString(1);
                    c = cursor.getString(2);
                    d = cursor.getString(3);
                    e = cursor.getString(4);
                }

                StringBuilder builder1 = new StringBuilder();
                StringBuilder builder2 = new StringBuilder();
                StringBuilder builder3 = new StringBuilder();
                StringBuilder builder4 = new StringBuilder();
                StringBuilder builder5 = new StringBuilder();

                builder1.append(a);
                builder2.append(b);
                builder3.append(c);
                builder4.append(d);
                builder5.append(e);

                Intent intent = new Intent(getApplicationContext(), CalEdit2.class);
                intent.putExtra("시작날짜", (Serializable) builder1);
                intent.putExtra("종료날짜", (Serializable) builder2);
                intent.putExtra("시작시간", (Serializable) builder3);
                intent.putExtra("종료시간", (Serializable) builder4);
                intent.putExtra("일정", (Serializable) builder5);
                startActivity(intent);

            }
        });

        selectDB();
        if(month<9){
            if(day<10){
                x=year+"-"+0+month+"-"+0+day;
                selectDB();
            }else{
                x=year+"-"+0+month+"-"+day;
                selectDB();
            }
        }else{
            if(day<10){
                x=year+"-"+month+"-"+0+day;

                selectDB();
            }else{
                x=year+"-"+month+"-"+day;
                selectDB();
            }
        }
    }

    public void selectDB(){

        try {
            db = helper.getWritableDatabase();
            sql = "select sText,sStartTime, sEndTime from cscheduletbl where '"+x+"'>= sStartDate AND '"+x+"' <= sEndDate order by sStartTime ";
            cursor = db.rawQuery(sql, null);   // select 사용시 사용(sql문, where조건 줬을 때 넣는 값)

            CalAdapter adapter = new CalAdapter();

            while (cursor.moveToNext()){
                adapter.addItemToList(cursor.getString(0),cursor.getString(1),cursor.getString(2));
            }
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}