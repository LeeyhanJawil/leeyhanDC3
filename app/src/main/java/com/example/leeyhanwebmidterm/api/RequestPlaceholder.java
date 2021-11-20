package com.example.leeyhanwebmidterm.api;

import com.example.leeyhanwebmidterm.pojos.Login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestPlaceholder {

    @POST("login")
    Call<Login> Login(@Body Login login);
}
