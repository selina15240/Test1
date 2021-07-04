package com.example.test1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.R.layout.activity_list_item;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener{
    TextView de_DeName;
    ListView de_ScrollList;

    DBHelper helper;
    SQLiteDatabase db;

    Button tap1;
    Button tap2;
    Button tap3;
    Button tap4;

    private void bindViews() {
        de_ScrollList = (ListView)findViewById(R.id.de_ListView);
        de_DeName = (TextView)findViewById(R.id.de_DeName);
        tap1 = (Button)findViewById(R.id.tap1);
        tap2 = (Button)findViewById(R.id.tap2);
        tap3 = (Button)findViewById(R.id.tap3);
        tap4 = (Button)findViewById(R.id.tap4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department);
        bindViews();
        setDeName();

        tap1.setOnClickListener(this);
        tap2.setOnClickListener(this);
        tap3.setOnClickListener(this);
        tap4.setOnClickListener(this);
        selectDepartment();
    }

    @Override
    public void onClick(View v) {
        if(v==tap1){
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
        }else if(v==tap2){
            Intent intent2 = new Intent(this, PayActivity.class);
            startActivity(intent2);
        }else if(v==tap3){
            Intent intent3 = new Intent(this, CalActivity.class);
            startActivity(intent3);
        }else if(v==tap4){
            Intent intent4 = new Intent(this, DepartmentActivity.class);
            startActivity(intent4);
        }
    }

    public void setDeName(){
        String name = "";
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT department FROM cmembertbl", null);
        while(cursor.moveToNext()){
            name = cursor.getString(0);
        }
        de_DeName.setText(name);
        cursor.close();
        db.close();
    }
    // work SELECT문
    public void selectDepartment(){
        ArrayList<WorkList> _WorkList = new ArrayList<>();
        try{
            helper = new DBHelper(this);
            db = helper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT position, name, usertel, email FROM cmembertbl", null);
            DepartmentAdapter adapter = new DepartmentAdapter();
            if(cursor.getCount()!=0){
                //조회온 데이터가 있을때 내부 수행
                while(cursor.moveToNext()) {
                    adapter.addItemList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                }
            }
            de_ScrollList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch(Exception e){e.printStackTrace();}
    }
}