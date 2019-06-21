package com.example.navigatinodrawaertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;

public class DataConverter {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        //java.lang.NullPointerException: Attempt to invoke virtual method 'boolean android.graphics.Bitmap.compress(android.graphics.Bitmap$CompressFormat, int, java.io.OutputStream)' on a null object reference
        //        at com.example.navigatinodrawaertest.DataConverter.getBytes(DataConverter.java:14)
        //excepton이 나는  상황 >> 메모 만들고 > backpress로 저장하고 만들어진걸로 다시 들어가고(이때 이미지 증발)  > 다시 저장하려고 backpress할때
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static Bitmap bitmapResizer(Bitmap bitmap){
        Bitmap resized=null;
        BitmapFactory.Options options =new BitmapFactory.Options();
        options.inSampleSize=4;

        resized=Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        //특정너비나 높이값에 맞추어야 할경우
        //아래 코드는 높이 118에 맞춰 정확하게 118이하로 리사이징하는법
//        int height=bitmap.getHeight();
//        int width=bitmap.getWidth();
//
//        while(height>118){
//            resized=Bitmap.createScaledBitmap(bitmap, (width*118) / height ,118, true);
//            height=resized.getHeight();
//            width=resized.getWidth();
//        }

        return resized;
    }
}
