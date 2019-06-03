package com.example.navigatinodrawaertest;

import android.app.Application;
import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Applcation클래스를 상속받아 애플리케이션 컴포넌트사이에서 공유할 수 있게  해준다
//어디서든 context를 이용한 접근 가능
public class ApplicationController extends Application {
    public final static String TAG="JHS";
    private static ApplicationController instance;
    private NetworkService networkService;
    private String baseUrl;

    public static ApplicationController getInstance(){
        return instance;
    }

    public NetworkService getNetworkService() {
        return networkService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance=this;
    }

    public void buildNetWorkService(String ip, int port){
        synchronized (ApplicationController.class){
            if(networkService==null){
                baseUrl=String.format("http://%s:%d/", ip, port);
                Log.i(TAG, baseUrl);

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                networkService=retrofit.create(NetworkService.class);
            }
        }
    }

    public void buildNetWorkService(String ip){
        synchronized (ApplicationController.class){
            if(networkService==null){
                baseUrl=String.format("http://%s/", ip);
                Log.i(TAG, baseUrl);
            }

            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            networkService=retrofit.create(NetworkService.class);
        }
    }
}
