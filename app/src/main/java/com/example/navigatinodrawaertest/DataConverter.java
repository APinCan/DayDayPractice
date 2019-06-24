package com.example.navigatinodrawaertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

import static org.opencv.imgproc.Imgproc.GaussianBlur;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static org.opencv.imgproc.Imgproc.threshold;

public class DataConverter {

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
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

    public static Mat preProcessing(Mat preMat){
        Mat transMat = new Mat();

        //이미지를 흑백으로
        Imgproc.cvtColor(preMat, transMat, Imgproc.COLOR_BGR2GRAY );

        //이미지 전처리
        Mat element1 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        Mat element2 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        dilate(transMat, transMat, element1);
        erode(transMat, transMat, element2);

        GaussianBlur(transMat, transMat, new Size(3, 3), 0);
        // The thresold value will be used here
        threshold(transMat, transMat, 100, 255, THRESH_BINARY);

        return transMat;
    }
}
