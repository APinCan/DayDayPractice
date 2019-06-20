package com.example.navigatinodrawaertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextID;
    private EditText editTextPW;
    private Button btnSubmit;
    private Button btnTest; //테스트를 위한 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViewByID();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitListener();
            }
        });

        //테스트를 위한 버튼
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            }
        });
    }

    //View의 컴포넌트 초기화화
    private void initViewByID(){
        editTextID = findViewById(R.id.loginIdText);
        editTextPW = findViewById(R.id.loginPwText);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnTest = findViewById(R.id.btnTEST); //테스트를 위한 버튼
    }

    //계정을 확인
    private void checkAccount(){

    }

    /**
     * 리스너들
     */
    private void btnSubmitListener(){
        String id = editTextID.getText().toString();
        String pw = editTextPW.getText().toString();

        if(id.length()==0){
            Toast.makeText(LoginActivity.this, "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
            editTextID.requestFocus();
            return;
        }
        else if(pw.length()==0){
            Toast.makeText(LoginActivity.this, "PW를 입력해주세요", Toast.LENGTH_SHORT).show();
            editTextPW.requestFocus();
            return;
        }

        checkAccount();
    }

}
