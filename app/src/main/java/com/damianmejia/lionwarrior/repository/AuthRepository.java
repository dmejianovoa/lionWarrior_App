package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.AuthResponse;
import com.damianmejia.lionwarrior.model.LoginRequest;
import com.damianmejia.lionwarrior.model.RegisterRequest;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Patrón Repository: intermediario entre las pantallas y la API de autenticación
public class AuthRepository {

    private final ApiService apiService;

    //Constructor normal: usa el cliente real Retrofit real (Singleton)
    public AuthRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    // Iniciar sesion con correo y contraseña
    public void login(String email, String password, AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(buildCallBack(callback));
    }

    //Registra un nuevo cliente
    public void register(RegisterRequest request, AuthCallback callback) {
        apiService.register(request).enqueue(buildCallBack(callback));
    }

    //Convierte la respuesta de Retrofit en el resultado simple: exito o error
    private Callback<AuthResponse> buildCallBack(AuthCallback callback) {
        return new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Codigos 2xx : Exito
                    callback.onSuccess(response.body());
                } else {
                    // Codigos de error 4xx : se extrae el mensaje de la API
                    callback.onError(parseErrorMessage(response));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // No hubo respuesta: Sin internet, timeout o servidor caido
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexion e intentalo de nuevo");
            }
        };
    }

    // Lee el JSON de error (errorBody) y extrae el campo "mensaje"
    private String parseErrorMessage(Response<?> response) {
        try (ResponseBody errorBody = response.errorBody()) {
            if (errorBody != null) {
                AuthResponse error = new Gson().fromJson(errorBody.charStream(), AuthResponse.class);
                if (error != null && error.getMessage() != null) {
                    return error.getMessage();
                }
            }
        } catch (Exception e) {
            //El error no vino en el formato esperado
        }
        return "Ocurrio un error inesperado (Codigo" + response.code() + ").";
    }
}
