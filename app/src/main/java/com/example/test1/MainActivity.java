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
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button main_Start;
    Button main_End;
    Button main_Edit;
    ListView main_ScrollList;
    TextView main_State;
    static String state;

    public String StartTime;
    public String EndTime;

    DBHelper helper;
    SQLiteDatabase db;

    Button tap1;
    Button tap2;
    Button tap3;
    Button tap4;
    private String Day1;
    private String Day2;
    private int goho;
    private int gomi;
    private int offho;
    private int offmi;


    private void bindViews() {
        main_Start = (Button)findViewById(R.id.main_Start);
        main_End = (Button)findViewById(R.id.main_End);
        main_Edit = (Button)findViewById(R.id.main_Edit);
        main_ScrollList = (ListView)findViewById(R.id.main_ScrollList);
        main_State = (TextView)findViewById(R.id.main_State);
        tap1 = (Button)findViewById(R.id.tap1);
        tap2 = (Button)findViewById(R.id.tap2);
        tap3 = (Button)findViewById(R.id.tap3);
        tap4 = (Button)findViewById(R.id.tap4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.close();
        bindViews();
        main_State.setText(getTime()+"\n"+"\t"+"출근전");

        main_Start.setOnClickListener(this);
        main_End.setOnClickListener(this);
        main_Edit.setOnClickListener(this);
        tap1.setOnClickListener(this);
        tap2.setOnClickListener(this);
        tap3.setOnClickListener(this);
        tap4.setOnClickListener(this);
        selectMain();
    }

    private String getTime(){
        Calendar cal = Calendar.getInstance();
        int y=0, m=0, d=0, h=0, mi=0;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) +1;
        d = cal.get(Calendar.DAY_OF_MONTH);
        h = cal.get(Calendar.HOUR);
        mi = cal.get(Calendar.MINUTE);
        String date = y+"/"+m+"/"+d+" "+h+":"+mi;
        return date;
    }

    private int getho(){
        Calendar cal = Calendar.getInstance();
        int h=0;
        h = cal.get(Calendar.HOUR);
        return h;
    }
    private int getmi(){
        Calendar cal = Calendar.getInstance();
        int mi=0;
        mi = cal.get(Calendar.MINUTE);
        return mi;
    }

    private String getDay(){
        Calendar cal = Calendar.getInstance();
        int y=0, m=0, d=0;

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH) +1;
        d = cal.get(Calendar.DAY_OF_MONTH);
        String date = y+"/"+m+"/"+d;
        return date;
    }

    @Override
    public void onClick(View v) {
        if(v==main_Start) { //출근 데이터 갱신
            state ="근무 중";
            main_State.setText(getTime()+"\n"+"\t"+state);
            goho = getho();
            gomi = getmi();
            StartTime = getTime();
            Day1 = getDay();
            insert_goTime(StartTime, goho, gomi, Day1);
        }else if(v==main_End){ //퇴근 데이터 갱신
            state = "출근 전";
            main_State.setText(getTime()+"\n"+"\t"+state);
            EndTime = getTime();
            Day2 = getDay();
            offho = getho();;
            offmi = getmi();
            insert_offTime(EndTime, Day2, offho, offmi);

            DBHelper databaseHelper = new DBHelper(this);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            Cursor cursor;
            int time1, time2 = 0; // 분단위
            String goho = null, gomi = null;
            cursor= db.rawQuery("SELECT wGoHo, wGoMi FROM cworktbl",null);
            while(cursor.moveToNext()){
                goho = cursor.getString(0);
                gomi = cursor.getString(1);
            }
            time1 = ((offho*60)+offmi)-((offho*60)+offmi);
            if(time1 <= 600){
                time2 = 0;
            }else if(time1 > 600){
                time2 = time1-600;
                time1 = 600;
            }
            db.execSQL("UPDATE cworktbl SET wDutyHours = '"+time1+"', wExtraHours = '"+time2+"' WHERE wDate = '"+Day2+"';");

            int antiPay, extraPay, monPay = 0;
            int x = 0;  int y = 0; int z =0;
            cursor= db.rawQuery("SELECT wGoMi, wOffMi FROM cworktbl",null);
            while(cursor.moveToNext()){
                x = cursor.getInt(0);
                y = cursor.getInt(1);
            }
            z=y-x;
            antiPay=z*145;
            monPay=antiPay;
            db.execSQL("insert into cpaytbl (pDate, pMID) values('"+2021+"."+7+"','"+"aaaaa"+"')");
            pay(antiPay, monPay);
            selectMain();

        }else if(v==main_Edit){
            Intent intent = new Intent(this, EditActivity.class);
            startActivity(intent);

        }else if(v==tap1){
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

    public void insert_goTime(String time, int goho, int gomi, String day) {
        DBHelper databaseHelper = new DBHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String id="";
        Cursor cursor;
        cursor= db.rawQuery("SELECT userid FROM cmembertbl",null);
        while(cursor.moveToNext()){
            id = cursor.getString(0);
        }
        db.execSQL("INSERT INTO cworktbl (wMID, wDate, wGoTime, wChange, wGoHo, wGoMi) VALUES ('"+id+"','"+day+"','"+time+"','"+0+"', '"+goho+"', '"+gomi+"');");
        Toast.makeText(getApplicationContext(), "출근 성공", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void insert_offTime(String time, String day, int offho, int offmi) {
        DBHelper databaseHelper = new DBHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("UPDATE cworktbl SET wOffTime = '"+time+"', wOffHo = '"+offho+"', wOffMi = '"+offmi+"' WHERE wDate = '"+day+"';");
        Toast.makeText(getApplicationContext(), "퇴근 성공", Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void pay(int antiPay, int monPay){
        DBHelper databaseHelper = new DBHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.execSQL("UPDATE cpaytbl SET pAntiPay = '"+antiPay+"', pMonthPay = '"+monPay+"' WHERE pDate = '"+2021+"."+7+"';");
        db.close();
    }

    // work SELECT문
    public void selectMain(){
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
            main_ScrollList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch(Exception e){e.printStackTrace();}
    }
}