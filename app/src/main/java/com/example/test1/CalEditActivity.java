package com.example.test1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalEditActivity extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();
    DBHelper helper;
    SQLiteDatabase db;

    ListView listView;

    boolean editmode;

    DatePickerDialog.OnDateSetListener myDatePicker1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };

    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        listView = findViewById(R.id.cal_list);
        EditText sta_date = (EditText) findViewById(R.id.editText1);

        helper = new DBHelper(this);

        sta_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalEditActivity.this, myDatePicker1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText end_date = (EditText) findViewById(R.id.editText4);
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalEditActivity.this, myDatePicker2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText sta_time = (EditText) findViewById(R.id.editText2);
        sta_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "??????";
                        // ????????? ????????? 12??? ???????????? "PM"?????? ?????? ??? -12???????????? ?????? (ex : PM 6??? 30???)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "??????";
                        }
                        // EditText??? ????????? ?????? ??????
                        sta_time.setText(state + " " + selectedHour + "??? " + selectedMinute + "???");
                    }
                }, hour, minute, false); // true??? ?????? 24?????? ????????? TimePicker ??????
                mTimePicker.setTitle("?????? ??????");
                mTimePicker.show();
            }
        });

        EditText end_time = (EditText) findViewById(R.id.editText5);
        end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CalEditActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String state = "??????";
                        // ????????? ????????? 12??? ???????????? "PM"?????? ?????? ??? -12???????????? ?????? (ex : PM 6??? 30???)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "??????";
                        }
                        // EditText??? ????????? ?????? ??????
                        end_time.setText(state + " " + selectedHour + "??? " + selectedMinute + "???");
                    }
                }, hour, minute, false); // true??? ?????? 24?????? ????????? TimePicker ??????
                mTimePicker.setTitle("?????? ??????");
                mTimePicker.show();
            }
        });

        EditText text = (EditText) findViewById(R.id.editText3); //??????

        Intent intent = getIntent();

        sta_date.setText(intent.getStringExtra("????????????"));


        Button btn1 = (Button) findViewById(R.id.button1); //????????????
        Button btn2 = (Button) findViewById(R.id.button2); //????????????
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String date1 = sta_date.getText().toString();
                String date2 = end_date.getText().toString();
                String time1 = sta_time.getText().toString();
                String time2 = end_time.getText().toString();
                String txt = text.getText().toString();

               if(date1.equals("")){
                   Toast.makeText(CalEditActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               } else if(date2.equals("")){
                   Toast.makeText(CalEditActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               } else if(time1.equals("")){
                   Toast.makeText(CalEditActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               } else if(time2.equals("")){
                   Toast.makeText(CalEditActivity.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               } else if(txt.equals("")){
                   Toast.makeText(CalEditActivity.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
               } else{

                   InsertData("aaaaa",date1,date2,time1,time2,txt);
                   Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(CalEditActivity.this, CalActivity.class);
                   startActivity(intent);
               }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd";    // ????????????   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText sta_date = (EditText) findViewById(R.id.editText1);
        sta_date.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd";    // ????????????   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText end_time = (EditText) findViewById(R.id.editText4);
        end_time.setText(sdf.format(myCalendar.getTime()));
    }

    public void InsertData(String uid, String date1, String date2, String time1, String time2, String txt){
        db = helper.getWritableDatabase();
        String sql= ("insert into cscheduletbl(sMID, sStartDate, sEndDate, sStartTime, sEndTime, sText) values "+"('"+"aaaaa"+"','"+date1+"','"+date2+"','"+time1+"','"+time2+"','"+txt+"')");
        db.execSQL(sql);
    }
}