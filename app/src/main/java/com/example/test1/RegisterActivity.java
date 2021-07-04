package com.example.test1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText userid,password,repassword,name, company,department,position,usertel,email;
    //아이디, 패스워드, 패스워드 재입력, 이름, 소속회사, 부서, 직급,전화번호, 이메일
    Button btnSignUp, btnSigncancel;  //회원가입. 취소 버튼 (이미 존재하는 사용자일경우, 취소 버튼을 눌러 이전 로그인 화면으로 이동)
    DBHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        userid = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.repassword);
        name = (EditText)findViewById(R.id.name);
        company = (EditText)findViewById(R.id.company);
        department = (EditText)findViewById(R.id.department);
        position = (EditText)findViewById(R.id.position);
        usertel = (EditText)findViewById(R.id.usertel);
        email = (EditText)findViewById(R.id.email);

        btnSignUp = (Button)findViewById(R.id.btn_signup);
        btnSigncancel = (Button)findViewById(R.id.btn_signcancel);

        db = new DBHelper(this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userid.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                String uname = name.getText().toString();
                String com = company.getText().toString();
                String depart = department.getText().toString();
                String po = position.getText().toString();
                String tel = usertel.getText().toString();
                String em = email.getText().toString();

                if(user.equals("") || pass.equals("") || repass.equals("") || uname.equals("") || com.equals("") || depart.equals("") || po.equals("") || tel.equals("") || em.equals("")) {   //입력칸이 비어있으면 안됨
                    Toast.makeText(RegisterActivity.this, "입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(pass.equals(repass)) {
                        boolean usercheckResult = db.checkuserid(user);
                        if(usercheckResult==false) {
                            boolean regResult = db.insertData(user,pass,uname,com,depart,po,tel,em);
                            if(regResult==true) {
                                Toast.makeText(RegisterActivity.this, "정상적으로 회원가입이 되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "회원가입에 실패하셨습니다", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 사용자입니다", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnSigncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}