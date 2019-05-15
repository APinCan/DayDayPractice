package com.example.navigatinodrawaertest;

import android.graphics.Bitmap;
import android.media.MediaMetadata;

//Memo데이터를 저장할 객체 생성
public class MemoData {
    private String textTitle;
    private String textMain;
    private String textAddress;
    private Bitmap memoBitmap;

    public MemoData(){}

    public MemoData(String title, String main, Bitmap memoBitmap, String address){
        this.textTitle=title;
        this.textMain=main;
        this.memoBitmap=memoBitmap;
        this.textAddress=address;
    }

    public String getTitle(){
        return textTitle;
    }

    public String getMain(){
        return textMain;
    }

    public String getAddress() { return textAddress; }

    public Bitmap getMemoBitmap() {return memoBitmap;}

    public void setTitle(String title){
        this.textTitle=title;
    }

    public void setMain(String main){
        this.textMain=main;
    }

    public void setAddress(String address){
        this.textAddress=address;
    }

    public void setMemoBitmap(Bitmap memoBitmap){ this.memoBitmap=memoBitmap;}


}
