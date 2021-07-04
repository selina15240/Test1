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
                        String state = "오전";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "오후";
                        }
                        // EditText에 출력할 형식 지정
                        sta_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("시작 시간");
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
                        String state = "오전";
                        // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                        if (selectedHour > 12) {
                            selectedHour -= 12;
                            state = "오후";
                        }
                        // EditText에 출력할 형식 지정
                        end_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분");
                    }
                }, hour, minute, false); // true의 경우 24시간 형식의 TimePicker 출현
                mTimePicker.setTitle("종료 시간");
                mTimePicker.show();
            }
        });

        EditText text = (EditText) findViewById(R.id.editText3); //일정

        Intent intent = getIntent();

        sta_date.setText(intent.getStringExtra("시작날짜"));
        end_date.setText(intent.getStringExtra("종료날짜"));
        sta_time.setText(intent.getStringExtra("시작시간"));
        end_time.setText(intent.getStringExtra("종료시간"));
        text.setText(intent.getStringExtra("일정"));


        Button btn1 = (Button) findViewById(R.id.button1); //수정버튼
        Button btn2 = (Button) findViewById(R.id.button2); //삭제버튼
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String date1 = sta_date.getText().toString();
                String date2 = end_date.getText().toString();
                String time1 = sta_time.getText().toString();
                String time2 = end_time.getText().toString();
                String txt = text.getText().toString();

                if(date1.equals("")){
                    Toast.makeText(CalEdit2.this, "시작 날짜를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(date2.equals("")){
                    Toast.makeText(CalEdit2.this, "종료 날짜를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(time1.equals("")){
                    Toast.makeText(CalEdit2.this, "시작 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(time2.equals("")){
                    Toast.makeText(CalEdit2.this, "종료 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if(txt.equals("")){
                    Toast.makeText(CalEdit2.this, "일정을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else{

                    InsertData(date1,date2,time1,time2,txt);
                    Toast.makeText(getApplicationContext(), "수정 완료", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CalEdit2.this, CalActivity.class);
                    startActivity(intent);
                }

            }

        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                delData();
                Toast.makeText(getApplicationContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
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
        String myFormat = "yyyy-MM-dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText sta_date = (EditText) findViewById(R.id.editText1);
        sta_date.setText(sdf.format(myCalendar.getTime()));
    }
    private void updateLabel2() {
        String myFormat = "yyyy-MM-dd";    // 출력형식   2018/11/28
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