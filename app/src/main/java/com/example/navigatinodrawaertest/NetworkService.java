package com.example.navigatinodrawaertest;

//여기서 만든 call은 동기 or 비동기하는 http요청을 원격 웹서버로 보낼수있다
//http요청은 어노테이션을 사용해 명시
//url 파라미터치환과 쿼리 파라미터 지원
//객체를 요청 body로 변환(json형태로)
//멀티파트 요청 body와 파일 업로드 가능
//Retrofit 한글 번역 사이트 ( http://devflow.github.io/retrofit-kr/ )

import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.widget.CardView;

import java.util.List;

import okhttp3.internal.Version;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

//예제
public interface NetworkService {
    @POST("/api/versions/")
    Call<Version> post_version(@Body Version version);

    @PATCH("/api/versions/{pk}/")
    Call<Version> path_version(@Path("pk") int pk, @Body Version version);

    @DELETE("/pai/versions/{pk}/")
    Call<Version> delete_verison(@Path("pk") int pk);

    @GET("/api/versions/")
    Call<List<Version>> get_version();

    @GET("/api/versions/{pk}/")
    Call<Version> get_pk_version(@Path("pk") int pk);



//    @POST("/api/restaurants/")
//    Call<Restaurant> post_restaurant(@Body Restaurant restaurant);
//
//    @PATCH("/api/restaurants/{pk}/")
//    Call<Restaurant> patch_restaurant(@Path("pk") int pk, @Body Restaurant restaurant);
//
//    @DELETE("/api/restaurants/{pk}/")
//    Call<Restaurant> delete_restaurant(@Path("pk") int pk);
//
//    @GET("/api/restaurants/")
//    Call<List<Restaurant>> get_restaurant();
//
//    @GET("/api/restaurants/{pk}/")
//    Call<Restaurant> get_pk_restaurant(@Path("pk") int pk);
//
//    @GET("/api/weathers/{pk}/restaurant_list/")
//    Call<List<Restaurant>> get_weather_pk_restaurant(@Path("pk") int pk);
}
