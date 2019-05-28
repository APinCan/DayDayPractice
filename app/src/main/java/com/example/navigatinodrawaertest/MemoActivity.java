package com.example.navigatinodrawaertest;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.navigatinodrawaertest.ImgaeRotatorClass.handleSamplingAndRotationBitmap;

public class MemoActivity extends AppCompatActivity {
    private final int PICK_FROM_ALBUM = 2000;
    private static final int GPS_ENABLE_REQUEST_CODE = 2002;
    private final int PICK_FROM_ALBUM_TEXTRECOGNITION=2001;
    final int MEMO_EDIT = 100;

    EditText editTextTitle;
    EditText editTextMain;
    TextView textViewAddress;
    TextView textViewCurrentDay;
    MemoAdapter memoAdapter;
    Intent intent;
    Bitmap inputImage;
    Dialog customDialog;
    Button buttonOK, buttonCancel;
    ImageView dialogImageView;
    ImageView meemoImageView;
    byte[] byteImage;
    private GpsTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memoAdapter=MemoAdapter.getInstance();

        editTextTitle=(EditText) findViewById(R.id.editTextTitle);
        editTextMain=(EditText) findViewById(R.id.editTextMain);
        textViewAddress=(TextView)findViewById(R.id.textViewAddressActivity);
        textViewCurrentDay=(TextView)findViewById(R.id.textViewCurrentDayActivity);
        meemoImageView=(ImageView)findViewById(R.id.imageViewMemoActivity);


        editTextTitle.setTransitionName("transitionTitle");
        editTextMain.setTransitionName("transitionMain");

        intent=getIntent();
        editTextTitle.setText(intent.getStringExtra("memoTitle"));
        editTextMain.setText(intent.getStringExtra("memoMain"));
        textViewAddress.setText(intent.getStringExtra("memoAddress"));
        textViewCurrentDay.setText(intent.getStringExtra("memoCurrentDay"));
        byteImage=intent.getByteArrayExtra("memoImage");

        if(byteImage!=null) {
            Log.d("byteImage", byteImage.length+"");
            meemoImageView.setImageBitmap(DataConverter.getImage(byteImage));
            meemoImageView.setVisibility(View.VISIBLE);
        }

        Log.d("onCreate", "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStart", "start");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.memo_main_toolbar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_insert_image:
                goToAlbum();
                return true;
            case R.id.action_capture_image_convert:
                gotToAlbumTextRecognition();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("onStop", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("onDestroy", "ondesty");
    }

    @Override
    public void onBackPressed() {
        long now=System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDay=sdf.format(date);

        gpsTracker = new GpsTracker(MemoActivity.this);

        double latitude=gpsTracker.getLatitude();
        double longitude=gpsTracker.getLongitude();

        String address = getCurrentAddress(latitude, longitude);

        String textTitle=editTextTitle.getText().toString();
        String textMain=editTextMain.getText().toString();
        byte[] tmp;

        if(inputImage==null){
           tmp=byteImage;
        }
        else{
            tmp=DataConverter.getBytes(inputImage);
        }

        if(!textTitle.equals("")){
            if(!textMain.equals("")){

                MemoData memoData = new MemoData(textTitle, textMain, tmp, address, currentDay);
                //현재 넣으려는 데이터의 id
                int id=memoAdapter.databaseHelper.addEntry(textTitle, textMain, address, currentDay, tmp);

                memoData.setId(id);
                memoAdapter.addItem(memoData);

                memoAdapter.notifyDataSetChanged();
            }
        }

        Log.d("MemoAcitivty", "onBackPressed()");
        super.onBackPressed();
    }

    private void goToAlbum(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    private void gotToAlbumTextRecognition(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent, PICK_FROM_ALBUM_TEXTRECOGNITION);
    }

    //http://www.masterqna.com/android/68879/edittext%EC%97%90-%EC%9D%B4%EB%AF%B8%EC%A7%80%EB%A5%BC-%EB%84%A3%EC%9D%84%EC%88%98%EC%9E%88%EB%82%98%EC%9A%94
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==PICK_FROM_ALBUM){
            if(resultCode==RESULT_OK){
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    inputImage= BitmapFactory.decodeStream(in);

                    in.close();

                    //이건 editText에 그림을 넣는방법
                    //setPictureinMain(inputImage);
                    meemoImageView.setVisibility(View.VISIBLE);
                    inputImage=DataConverter.bitmapResizer(inputImage);

                    meemoImageView.setImageBitmap(inputImage);

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==PICK_FROM_ALBUM_TEXTRECOGNITION){
            if(resultCode==RESULT_OK){
                try {
                    InputStream in  =getContentResolver().openInputStream(data.getData());
                    inputImage=BitmapFactory.decodeStream(in);

                    Uri phtoUri=data.getData();
                    inputImage=handleSamplingAndRotationBitmap(this, phtoUri);

                    in.close();

                    dialogImageConvert();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==GPS_ENABLE_REQUEST_CODE){
            //사용자가 GPS 활성 시켰는지 검사
            if (checkLocationServicesStatus()) {
                if (checkLocationServicesStatus()) {

                    Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                    //여기서는 일단 안쓰는 메서드
//                    checkRunTimePermission();
                    return;
                }
            }
        }
        else if(resultCode==MEMO_EDIT){
            if(resultCode==RESULT_OK){

            }
        }
    }

    public void textRecognition(){
        TesseractOCR tesseractOCR = new TesseractOCR(this, "eng");
        String text=tesseractOCR.getOCRResult(inputImage);

        Toast.makeText(this, "recognizing....", Toast.LENGTH_SHORT).show();

        editTextMain.setText(text);
    }

    public void setPictureinMain(Bitmap bitmap){
        int cursor=editTextMain.getSelectionStart();

        Spannable span = (Spannable)editTextMain.getText();
        span.setSpan(new ImageSpan(bitmap), cursor, cursor+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public void dialogImageConvert(){
        customDialog = new Dialog(this);
        //customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.image_convert_dialog);
        customDialog.setTitle("ConvertImage");


        dialogImageView=(ImageView)customDialog.findViewById(R.id.dialogImage);
        buttonOK=(Button)customDialog.findViewById(R.id.dialogOK);
        buttonCancel=(Button)customDialog.findViewById(R.id.dialogCancel);
        buttonOK.setEnabled(true);
        buttonCancel.setEnabled(true);
        dialogImageView.setImageBitmap(inputImage);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textRecognition();
                customDialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }


    //여기서부터 주소관련 코드들
    public String getCurrentAddress( double latitude, double longitude) {
        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();

            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();

            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();

            return "주소 미발견";
        }
        Address address = addresses.get(0);

        return address.getAddressLine(0).toString()+"\n";
    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MemoActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);

        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}