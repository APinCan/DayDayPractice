package com.example.navigatinodrawaertest.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.navigatinodrawaertest.Datas.MemoData;
import com.example.navigatinodrawaertest.R;

import java.util.ArrayList;

//서버 : 117.16.136.120/site/api
//웹 : cpsp.site/dayday

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , DirectoryFragment.OnListFragmentInteractionListener {

    final int PERMISSION = 1001;
    final int MEMO_EDIT = 100;

    private MemoAdapter memoAdapter;
    private SelectedDayAdapter dayAdapter;
    Fragment fragment=null;
    RecyclerView recyclerView;
    DialogFragment dialFragment=null;

    public static DisplayMetrics displayMetrics;
    public static int StatusBarHeight;

    MenuItem action_create;

//    private NetworkService networkService;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == MEMO_EDIT) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("onResumeMainActivity", "MainActivity");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();

        displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        StatusBarHeight = getStatusBarHeight();

//        networkService = APIClient.getClient().create(NetworkService.class);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this.getIntent());
        String s = intent.getStringExtra("USER_ID");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DayDay");
        setSupportActionBar(toolbar);
        memoAdapter = MemoAdapter.getInstance();
        memoAdapter.setmContext(MainActivity.this); //여기서 디비까지 생성
        memoAdapter.databaseHelper.startLoadData();
        dayAdapter = new SelectedDayAdapter(MainActivity.this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.textView_userId);
        userName.setText(s);
        TextView userComment = headerView.findViewById(R.id.textView_userComment);
        userComment.setText("Hello, "+s+"!");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getLocation();

        //recycleView 초기화
        recyclerView = findViewById(R.id.memo_recyclerView);

        recyclerView.setAdapter(memoAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("onStartMainActivity", "MainActivity");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        action_create=menu.findItem(R.id.aciton_create);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent = new Intent(this, MemoActivity.class);

        switch (id) {
            case R.id.aciton_create:
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        recyclerView.setAdapter(null);

        int id = item.getItemId();
        String title=getString(R.string.app_name);

        if(id==R.id.nav_home){
            recyclerView.setAdapter(memoAdapter);

            ArrayList<MemoData> tmpData = new ArrayList<>();
            tmpData.addAll(memoAdapter.getMemos());

            memoAdapter.clearMemos();
            memoAdapter.notifyDataSetChanged();

//            if(fragment!=null) {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().remove(fragment).commit();
//                fragmentManager.popBackStack();
//            }
            if(dialFragment!=null){
                dialFragment.dismiss();
            }

            memoAdapter.setMemos(tmpData);
            memoAdapter.notifyDataSetChanged();


            action_create.setVisible(true);
        }
        else if (id == R.id.nav_Directory_List) {

            Bundle args = new Bundle();
            ArrayList<String> tmpArray= new ArrayList<>();

            args.putStringArrayList("DAY_ARRAY", findDay());

            //프래그먼트로 띄우기
//            fragment=new DirectoryFragment();
//            fragment.setArguments(args);
            dialFragment =DirectoryFragment.newInstance(3);
            dialFragment.setArguments(args);
            title="Directory";

            recyclerView.setAdapter(dayAdapter);

            action_create.setVisible(false);

//            if(fragment!=null){
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.drawer_container, fragment);
//                ft.commit();
//            }
            if(dialFragment!=null){
                dialFragment.show(getSupportFragmentManager(), "tag");
            }
        }

        //툴바에 타이틀 적용
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //현재 위치정보를 받아오기 https://bottlecok.tistory.com/54
    //https://webnautes.tistory.com/1315 권한까지
    public void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location;
        //가장최근 위치정보
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        //위치 리스너는 위치정보를 전달할때 호출됨 onLocationChnaged()메서드 안에 위치정보 처리를 작업
        LocationListener gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
    }


    //권한을 체크
    public void checkPermission() {
        //각 권한들이 설정되어있는지 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            //권한이 하나라도 설정이 안되어 있으면 permission을 요청하는 팝업
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA
                    }, PERMISSION
            );
        }

    }

    //reqeustPermissions로 권한요청 한것에 대한 결과가 반환
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION:
                //grantResult는 requestPermission에 요청된 String[]순서로 들어온다
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //권한 허가
                } else {
                    //권한거부
                }

                Context context = MainActivity.this;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.getPackageName()));
                startActivityForResult(intent, 0);

                return;
        }
    }

    @Override
    public void onListFragmentInteraction(String date) {
        dayAdapter.clearData();
        dayAdapter.notifyDataSetChanged();

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(date);
        }

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().remove(fragment).commit();
//        fragmentManager.popBackStack();

        dialFragment.dismiss();

        dayAdapter.setSelectedDate(memoAdapter.getMemos(), date);
        dayAdapter.notifyDataSetChanged();
    }

    /*
    // 퍼미션 확인하는 다른 방법
      private boolean hasPermissions(String[] permissions) {
        // 퍼미션 확인
        int result = -1;
        for (int i = 0; i < permissions.length; i++) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), permissions[i]);
        }
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else {
            return false;
        }
    }


    private void requestNecessaryPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                //퍼미션을 거절했을 때 메시지 출력 후 종료
                if (!hasPermissions(PERMISSIONS)) {
                    Toast.makeText(getApplicationContext(), "CAMERA PERMISSION FAIL", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }
     */

    private ArrayList<String> findDay(){
        ArrayList<MemoData> cloneMemo = new ArrayList<>();
        ArrayList<String> findDayString = new ArrayList<>();

        cloneMemo.addAll(memoAdapter.getMemos());
        for(int i=0;i<cloneMemo.size();i++){
            String currentDay=cloneMemo.get(i).getTextCurrentDay();
            String day=currentDay.split(" ")[0];
            if(!findDayString.contains(day)){
                findDayString.add(day);
            }
        }

        return findDayString;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
