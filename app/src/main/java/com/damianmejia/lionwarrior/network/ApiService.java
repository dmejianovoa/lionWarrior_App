package com.damianmejia.lionwarrior.network;

import com.damianmejia.lionwarrior.model.Barbers;
import com.damianmejia.lionwarrior.model.LoginRequest;
import com.damianmejia.lionwarrior.model.AuthResponse;
import com.damianmejia.lionwarrior.model.RegisterRequest;
import com.damianmejia.lionwarrior.model.Service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

//Define los endpoints que consume la APP
public interface ApiService {

    // POST /api/auth/login -> envia email y password, recibe AuthResponse
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    // POST /api/auth/register -> envia los datos del cliente, recibe mensaje (201 0 409)
    @POST("api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    // GET /api/services → lista de servicios de la barbería
    @GET("api/services")
    Call<List<Service>> getServices();

    // GET /api/barbers → lista de barberos
    @GET("api/barbers")
    Call<List<Barbers>> getBarbers();
}
