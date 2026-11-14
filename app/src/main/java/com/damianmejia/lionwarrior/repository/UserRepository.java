package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.UpdateUserRequest;
import com.damianmejia.lionwarrior.model.User;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;
import com.damianmejia.lionwarrior.util.ApiErrorParser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Patrón Repository: consulta y actualiza los datos del cliente en la API
public class UserRepository {

    private final ApiService apiService;

    public UserRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    // Constructor para pruebas unitarias con un ApiService simulado
    public UserRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getUser(int idUser, DataCallback<User> callback) {
        apiService.getUser(idUser).enqueue(buildCallback(callback));
    }

    public void updateUser(int idUser, UpdateUserRequest request, DataCallback<User> callback) {
        apiService.updateUser(idUser, request).enqueue(buildCallback(callback));
    }

    // Mismo manejo de respuesta para el GET y el PUT (los dos devuelven un User)
    private Callback<User> buildCallback(DataCallback<User> callback) {
        return new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    // Ej.: 409 "Ya existe un usuario con ese email"
                    callback.onError(ApiErrorParser.parse(response));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexión e inténtalo de nuevo.");
            }
        };
    }
}