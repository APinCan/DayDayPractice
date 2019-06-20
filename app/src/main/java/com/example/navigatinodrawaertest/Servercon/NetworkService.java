package com.example.navigatinodrawaertest.Servercon;

//여기서 만든 call은 동기 or 비동기하는 http요청을 원격 웹서버로 보낼수있다
//http요청은 어노테이션을 사용해 명시
//url 파라미터치환과 쿼리 파라미터 지원
//객체를 요청 body로 변환(json형태로)
//멀티파트 요청 body와 파일 업로드 가능
//Retrofit 한글 번역 사이트 ( http://devflow.github.io/retrofit-kr/ )

import android.support.design.widget.BaseTransientBottomBar;
import android.support.v7.widget.CardView;

//https://jongmin92.github.io/2018/01/29/Programming/android-retrofit2-okhttp3/

import com.example.navigatinodrawaertest.Datas.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

//예제
public interface NetworkService {
//    @POST("/api/versions/")
//    Call<Version> post_version(@Body Version version);
//
//    @PATCH("/api/versions/{pk}/")
//    Call<Version> path_version(@Path("pk") int pk, @Body Version version);
//
//    @DELETE("/pai/versions/{pk}/")
//    Call<Version> delete_verison(@Path("pk") int pk);
//
//    @GET("/api/versions/")
//    Call<List<Version>> get_version();
//
//    @GET("/api/versions/{pk}/")
//    Call<Version> get_pk_version(@Path("pk") int pk);

    /*
    받아올것
     */
    @POST("/users/")
    Call<User> post_user(@Body User user);

    @PATCH("/users/{pk}/")
    Call<User> path_user(@Path("pk") int pk, @Body User user);

    @GET("/users/")
    Call<List<User>> get_user();

    @GET("/users/{pk}/")
    Call<User> get_pk_user(@Path("pk") int pk);
    /*
    여기까지
     */

//    @GET("api/unknown")
//    Call<MultipleResource> doGetListResources();
//
//    @POST("api/users")
//    Call<User> createUser(@Body User user);
//
//    @GET("api/users?")
//    Call<UserList> doGetUserList(@Query("page") String page);
//
//    @FormUrlEncoded
//    @POST("api/users?")
//    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}