package com.example.navigatinodrawaertest.Datas;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public Integer id;
    @SerializedName("password")
    public String password;
    @SerializedName("email")
    public String email;
}
