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

public class CalEdit2 extends AppCompatActivity {

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
        setContentView(R.layout.activity_cal_edit2);

        listView = findViewById(R.id.cal_list);
        EditText sta_date = (EditText) findViewById(R.id.editText1);

        helper = new DBHelper(this);

        sta_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalEdit2.this, myDatePicker1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        EditText end_date = (EditText) findViewById(R.id.editText4);
        end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CalEdit2.this, myDatePicker2, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
                mTimePicker = new TimePickerDialog(CalEdit2.this, new TimePickerDialog.OnTimeSetListener() {
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
                mTimePicker = new TimePickerDialog(CalEdit2.this, new TimePickerDialog.OnTimeSetListener() {
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
        end_date.setText(intent.getStringExtra("????????????"));
        sta_time.setText(intent.getStringExtra("????????????"));
        end_time.setText(intent.getStringExtra("????????????"));
        text.setText(intent.getStringExtra("??????"));


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
                    Toast.makeText(CalEdit2.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else if(date2.equals("")){
                    Toast.makeText(CalEdit2.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else if(time1.equals("")){
                    Toast.makeText(CalEdit2.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else if(time2.equals("")){
                    Toast.makeText(CalEdit2.this, "?????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else if(txt.equals("")){
                    Toast.makeText(CalEdit2.this, "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else{

                    InsertData(date1,date2,time1,time2,txt);
                    Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CalEdit2.this, CalActivity.class);
                    startActivity(intent);
                }

            }

        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                delData();
                Toast.makeText(getApplicationContext(), "?????? ??????", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CalEdit2.this, CalActivity.class);
                startActivity(intent);
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

    public void InsertData(String date1, String date2, String time1, String time2, String txt){
        db = helper.getWritableDatabase();

        String sql= ("update cscheduletbl set sStartDate = '"+date1+"',sEndDate='"+date2+"',sStartTime='"+time1+"',sEndTime='"+time2+"',sText='"+txt+"' where sNum in (select sNum From cscheduletbl order by sNum DESC limit 1);");

        db.execSQL(sql);
    }
    public void delData(){
        db = helper.getWritableDatabase();
        String sql= ("delete from cscheduletbl where sNum in (select sNum From cscheduletbl order by sNum DESC limit 1);");
        db.execSQL(sql);
    }

}