package com.example.navigatinodrawaertest.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigatinodrawaertest.Datas.User;
import com.example.navigatinodrawaertest.R;
import com.example.navigatinodrawaertest.Servercon.APIClient;
import com.example.navigatinodrawaertest.Servercon.NetworkService;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextID;
    private EditText editTextPW;
    private Button btnSubmit;
    private Button btnTest; //테스트를 위한 버튼
    private NetworkService networkService;
    private final String connectionURL = "http://117.16.136.120:80/site/diary/diary/";

    private String id;
    private String pw;

    private String adminId="admin";
    private String adminPw="admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViewByID();
        networkService = APIClient.getClient().create(NetworkService.class);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmitListener();
            }
        });

    }

    //View의 컴포넌트 초기화화
    private void initViewByID(){
        editTextID = findViewById(R.id.loginIdText);
        editTextPW = findViewById(R.id.loginPwText);
        btnSubmit = findViewById(R.id.btnSubmit);
    }

    //계정을 확인
    private void checkAccount(){
        if(!editTextID.getText().toString().equals(adminId)) {
            Toast.makeText(this, "not Equal Id", Toast.LENGTH_SHORT).show();

            return;
        }
        if(!editTextPW.getText().toString().equals(adminPw)){
            Toast.makeText(this, "not Equal Password", Toast.LENGTH_SHORT).show();

            return;
        }

        AsyncTess asyncTess = new AsyncTess();

        asyncTess.execute();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("USER_ID", id);
        startActivity(intent);
    }

    /**
     * 리스너들
     */
    private void btnSubmitListener(){
        id = editTextID.getText().toString();
        pw = editTextPW.getText().toString();

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

    private void postUser2(){
        URL url = null;
        try {
            url = new URL(connectionURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection http = null;   // 접속
        try {
            http = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //--------------------------
        //   전송 모드 설정 - 기본적인 설정이다
        //--------------------------
        http.setDefaultUseCaches(false);
        http.setDoInput(true);                         // 서버에서 읽기 모드 지정
        http.setDoOutput(true);                       // 서버로 쓰기 모드 지정
        try {
            http.setRequestMethod("POST");         // 전송 방식은 POST
        } catch (ProtocolException e) {
            e.printStackTrace();
        }

        // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다
        http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        //--------------------------
        //   서버로 값 전송
        //--------------------------
        StringBuffer buffer = new StringBuffer();
        buffer.append("id").append("=").append(editTextID.getText().toString()).append(", ");                 // php 변수에 값 대입
        buffer.append("pword").append("=").append(editTextPW.getText().toString()).append(", ");   // php 변수 앞에 '$' 붙이지 않는다

        OutputStreamWriter outStream = null;
        try {
            outStream = new OutputStreamWriter(http.getOutputStream(), "EUC-KR");
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter writer = new PrintWriter(outStream);
        writer.write(buffer.toString());
        writer.flush();
    }

    private void postUser(){

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(connectionURL);
        ArrayList<NameValuePair> nameValues =
                new ArrayList<NameValuePair>();

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            nameValues.add(new BasicNameValuePair(
                    "userId", URLDecoder.decode(editTextID.getText().toString(), "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "userPassword", URLDecoder.decode(editTextPW.getText().toString(), "UTF-8")));

            //HttpPost에 넘길 값을들 Set해주기
            post.setEntity(
                    new UrlEncodedFormEntity(
                            nameValues, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.d("Insert Log", ex.toString());
        }

        try {
            //설정한 URL을 실행시키기
            HttpResponse response = client.execute(post);
            //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
            Log.d("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUser() {
        Call<List<User>> userCall = networkService.get_user();
        userCall.enqueue(new Callback<List<User>>(){
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    List<User> userList = response.body();

                    String userText="";
                    for(User user : userList){
                        userText+= user.id.toString()+
                                user.email+
                                user.password+
                                "\n";
                    }
                    Toast.makeText(getApplicationContext(), userText, Toast.LENGTH_SHORT).show();
                }
                else{
                    int statusCode=response.code();
                    Log.i("MainActivity.getUser", "statuscode : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    public class AsyncTess extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
//            postUser2();
            postMemos();
            return null;
        }
    }

    public void postMemos(){
        class PostMemosTask extends AsyncTask<Void, Void, Void>{

            @Override
            protected Void doInBackground(Void... voids) {
//                postMemoinAsync();
                MemoAdapter memoAdapter = MemoAdapter.getInstance();

//                for(int i=0;i<memoAdapter.getMemos().size();i++){
//                    Call<MemoData> postMemo = networkService.post_memodata(memoAdapter.getMemos().get(i));
//                    postMemo.enqueue(new Callback<MemoData>() {
//                        @Override
//                        public void onResponse(Call<MemoData> call, Response<MemoData> response) {
//
//                        }
//
//                        @Override
//                        public void onFailure(Call<MemoData> call, Throwable t) {
//
//                        }
//                    });
//                }
                for(int i=0;i<memoAdapter.getMemos().size();i++){
                   for(int j=0;j<5;j++){
                       String send = memoAdapter.getMemos().get(i).getMain();
                       Call<String> memo = networkService.post_memoString(send);
                       memo.enqueue(new Callback<String>() {
                           @Override
                           public void onResponse(Call<String> call, Response<String> response) {
                               if(response.isSuccessful()){
                                   Toast.makeText(getApplicationContext(), "등록완료", Toast.LENGTH_SHORT).show();
                               }
                               else{
                                   int statusCode=response.code();
                                   Log.d("retrofit Post", "응답코드"+statusCode);
                               }
                           }

                           @Override
                           public void onFailure(Call<String> call, Throwable t) {
                                Log.d("retroft Post error", t.getMessage());
                           }
                       });
                   }
                }

                return null;
            }
        }
    }

    private void postMemoinAsync(){
        InputStream inputStream = null;

        JSONObject json = new JSONObject();

        try{
            URL url = new URL(connectionURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();

//            json.accumulate(Bear.NAME, bear.getName());

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(json.toString());
            writer.flush();
            writer.close();

            int response = conn.getResponseCode();
            Log.d("REST POST", "The response is : " + response);
        }catch(Exception e){
            Log.e("REST POST", "Error : " + e.getMessage());
        }finally{
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
