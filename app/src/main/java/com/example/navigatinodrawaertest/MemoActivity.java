package com.example.navigatinodrawaertest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MemoActivity extends AppCompatActivity {
    EditText editTextTitle;
    EditText editTextMain;
    TextView textViewAddress;
    MemoAdapter memoAdapter;
    Intent intent;

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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        MemoData memoData = new MemoData(editTextTitle.getText().toString(), editTextMain.getText().toString(), "address");
        memoAdapter.addItem(memoData);
        memoAdapter.notifyDataSetChanged();

        Log.d("ADAPTOR", "onBackPressed()");
        super.onBackPressed();
    }
}
