package com.example.test1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.activity_list_item;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    ListView me_ScrollList;
    EditText me_Date;
    EditText me_Start;
    EditText me_End;
    EditText me_Reason;
    Button me_Yes;
    Button me_No;

    DBHelper helper;
    SQLiteDatabase db;

    Button tap1;
    Button tap2;
    Button tap3;
    Button tap4;

    private void bindViews() {
        me_ScrollList = (ListView)findViewById(R.id.me_ScrollList);
        me_Date = (EditText)findViewById(R.id.me_Date);
        me_Start = (EditText)findViewById(R.id.me_Start);
        me_End = (EditText)findViewById(R.id.me_End);
        me_Reason = (EditText)findViewById(R.id.me_Reason);
        me_Yes = (Button)findViewById(R.id.me_Yes);
        me_No = (Button)findViewById(R.id.me_No);
        tap1 = (Button)findViewById(R.id.tap1);
        tap2 = (Button)findViewById(R.id.tap2);
        tap3 = (Button)findViewById(R.id.tap3);
        tap4 = (Button)findViewById(R.id.tap4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_edit);
        bindViews();

        tap1.setOnClickListener(this);
        tap2.setOnClickListener(this);
        tap3.setOnClickListener(this);
        tap4.setOnClickListener(this);
        selectEdit();
    }

    @Override
    public void onClick(View v) {
        String Date = me_Date.getText().toString();
        String Start = me_Start.getText().toString();
        String End = me_End.getText().toString();
        String Reason = me_Reason.getText().toString();

        if(v==me_Yes) { // 수정 기록 갱신
            if(Date==null||Date==""){
                Toast.makeText(this, "날짜를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else if(Start==null||Start==""){
                Toast.makeText(this, "출근 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else if(End==null||End==""){
                Toast.makeText(this, "퇴근 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else if(Reason==null||Reason==""){
                Toast.makeText(this, "수정 사유를 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                DBHelper helper = new DBHelper(this);
                SQLiteDatabase db = helper.getWritableDatabase();
                db.execSQL("UPDATE cworktbl SET wChange = ?, wChangeDate = ?, wChangeCause = ?, wNewGoTime = ?, wNewOffTime = ? WHERE wDate = ?;",
                        new String[] {"1", Date, Reason, Start, End, Date});
                db.close();

                finish();
            }
        }else if(v==me_No||v==tap1){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }else if(v==tap2){
            Intent intent2 = new Intent(this, MainActivity.class);
            startActivity(intent2);
        }else if(v==tap3){
            Intent intent3 = new Intent(this, MainActivity.class);
            startActivity(intent3);
        }else if(v==tap4){
            Intent intent4 = new Intent(this, DepartmentActivity.class);
            startActivity(intent4);
        }
    }
    // work SELECT문
    public void selectEdit(){
        try{
            helper = new DBHelper(this);
            db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT wGoTime, wOffTime FROM cworktbl", null);
            MainAdapter adapter = new MainAdapter();
            if(cursor.getCount()!=0){
                //조회온 데이터가 있을때 내부 수행
                while(cursor.moveToNext()) {
                    adapter.addItemList(cursor.getString(0), cursor.getString(1));
                }
            }
            me_ScrollList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch(Exception e){e.printStackTrace();}
    }
}