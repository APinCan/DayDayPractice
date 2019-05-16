package com.example.navigatinodrawaertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

public class MemoActivity extends AppCompatActivity {
    private final int PICK_FROM_ALBUM = 2000;
    private final int PICK_FROM_ALBUM_TEXTRECOGNITION=2001;

    EditText editTextTitle;
    EditText editTextMain;
    TextView textViewAddress;
    MemoAdapter memoAdapter;
    Intent intent;
    Bitmap inputImage=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        memoAdapter=MemoAdapter.getInstance();

        editTextTitle=(EditText) findViewById(R.id.editTextTitle);
        editTextMain=(EditText) findViewById(R.id.editTextMain);
        textViewAddress=(TextView)findViewById(R.id.textViewAddressActivity);

        editTextTitle.setTransitionName("transitionTitle");
        editTextMain.setTransitionName("transitionMain");

        intent=getIntent();
        editTextTitle.setText(intent.getStringExtra("memoTitle"));
        editTextMain.setText(intent.getStringExtra("memoMain"));
        textViewAddress.setText(intent.getStringExtra("memoAddress"));


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
    public void onBackPressed() {
        String textTitle=editTextTitle.getText().toString();
        String textMain=editTextMain.getText().toString();
        if(!textTitle.equals("")){
            if(!textMain.equals("")){

                MemoData memoData = new MemoData(editTextTitle.getText().toString(), editTextMain.getText().toString(), inputImage, "address");
                memoAdapter.addItem(memoData);
                memoAdapter.notifyDataSetChanged();
            }
        }

        Log.d("ADAPTOR", "onBackPressed()");
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

                    setPictureinMain(inputImage);
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
                    in.close();

                    showImageConvertDialog();
                    //textRecognition();
                } catch (Exception e){
                    e.printStackTrace();
                }
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

    public void showImageConvertDialog(){
        ImageView image = new ImageView(this);
        image.setImageBitmap(inputImage);

        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("ImageToText");
        builder.setMessage("변환하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "변환시작", Toast.LENGTH_SHORT).show();
                textRecognition();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "종료", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        }).setView(image);

        AlertDialog alert=builder.create();
        alert.show();
    }
}
