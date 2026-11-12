package com.damianmejia.lionwarrior.network;

import com.damianmejia.lionwarrior.model.LoginRequest;
import com.damianmejia.lionwarrior.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//Define los endpoints que consume la APP
public interface ApiService {

    // POST /api/auth/login -> envia email y password, recibe AuthResponse
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);
}
