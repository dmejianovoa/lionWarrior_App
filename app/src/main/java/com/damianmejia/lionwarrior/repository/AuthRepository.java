package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.AuthResponse;
import com.damianmejia.lionwarrior.model.LoginRequest;
import com.damianmejia.lionwarrior.model.RegisterRequest;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;
import com.damianmejia.lionwarrior.util.ApiErrorParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Patrón Repository: intermediario entre las pantallas y la API de autenticación
public class AuthRepository {

    private final ApiService apiService;

    // Constructor normal: usa el cliente Retrofit real (Singleton)
    public AuthRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    // Constructor para pruebas unitarias con un ApiService simulado (Mockito)
    public AuthRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Inicia sesión con correo y contraseña
    public void login(String email, String password, AuthCallback callback) {
        LoginRequest request = new LoginRequest(email, password);
        apiService.login(request).enqueue(buildCallback(callback));
    }

    // Registra un nuevo cliente
    public void register(RegisterRequest request, AuthCallback callback) {
        apiService.register(request).enqueue(buildCallback(callback));
    }

    // Convierte la respuesta de Retrofit en un resultado simple: éxito o error
    private Callback<AuthResponse> buildCallback(AuthCallback callback) {
        return new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Códigos 2xx: éxito
                    callback.onSuccess(response.body());
                } else {
                    // Códigos 4xx/5xx: se extrae el mensaje que envía la API
                    callback.onError(ApiErrorParser.parse(response));
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // No hubo respuesta: sin internet, timeout o servidor caído
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexión e inténtalo de nuevo.");
            }
        };
    }
}