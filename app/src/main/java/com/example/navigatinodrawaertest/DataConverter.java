package com.example.navigatinodrawaertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;

public class DataConverter {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
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
