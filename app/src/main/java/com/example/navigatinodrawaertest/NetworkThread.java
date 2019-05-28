package com.example.navigatinodrawaertest;

import android.os.AsyncTask;

public class NetworkThread extends AsyncTask {
    //전달된 url 사용작업
    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    //백그라운드 스레드에서 작업중에도 메인스레드에 UI처리 요청
    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    //백그라운드에서 작업 종료후 결과를 메인 스레드로 통해
    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
