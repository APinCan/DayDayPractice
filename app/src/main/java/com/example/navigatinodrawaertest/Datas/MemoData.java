package com.example.navigatinodrawaertest.Datas;

import android.graphics.Bitmap;

//Memo데이터를 저장할 객체 생성
public class MemoData {
    private int id;
    private String textTitle;
    private String textMain;
    private String textAddress;
    private String textCurrentDay;
    private byte[] memoBitmap;

    public MemoData(){}

    public MemoData(String title, String main, byte[] memoBitmap, String address, String textCurrentDay){
        this.textTitle=title;
        this.textMain=main;
        this.memoBitmap=memoBitmap;
        this.textAddress=address;
        this.textCurrentDay=textCurrentDay;
    }

    public int getId() { return id;}

    public String getTitle(){
        return textTitle;
    }

    public String getMain(){
        return textMain;
    }

    public String getAddress() { return textAddress; }

    public byte[] getMemoBitmap() {return memoBitmap;}

    public String getTextCurrentDay() {return textCurrentDay;}

    public void setId(int id) {this.id=id;}

    public void setTitle(String title){
        this.textTitle=title;
    }

    public void setMain(String main){
        this.textMain=main;
    }

    public void setAddress(String address){
        this.textAddress=address;
    }

    public void setMemoBitmap(byte[] memoBitmap){ this.memoBitmap=memoBitmap;}

    public void setTextCurrentDay(String textCurrentDay) { this.textCurrentDay=textCurrentDay;}
}
