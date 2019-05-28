package com.example.navigatinodrawaertest;

import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ServerConnection {
    HttpURLConnection serverConneciton = null;
    //URL뒤에 붙여서 보낼 파라미터
    StringBuffer sbParams = new StringBuffer();

    public ServerConnection(){

    }

    //HttpURLConnection 이용해 web의 데이터 가져옴
    public void connection(String url){
        try {
            URL serverURL = new URL(url);
            serverConneciton= (HttpURLConnection)((URL) serverURL).openConnection();

            //urlConnection설정
            serverConneciton.setRequestMethod("POST"); //url에 대한 메서드 요청 : POST
            serverConneciton.setRequestProperty("Accept-Charset", "UTF-8"); //받는 캐릭터셋은 UTF-8로

//            InputStream in = new BufferedInputStream(ser)

        } catch (MalformedURLException e){
            Log.d("URLMalformedURLExcpetion", e.toString());
        } catch (IOException ex){
            Log.d("URLIOEXception ", ex.toString());
        }
    }
}
