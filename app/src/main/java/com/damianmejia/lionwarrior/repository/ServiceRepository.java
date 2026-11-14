package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.Service;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class ServiceRepository {

    private final ApiService apiService;

    public ServiceRepository() {
            this.apiService = RetrofitClient.getApiService();
    }

    // Constructor para pruebas unitarias con un ApiService simulado
     public ServiceRepository(ApiService apiService) {
        this.apiService = apiService;
     }

     public void getServices(DataCallback<List<Service>> callback) {
        apiService.getServices().enqueue(new Callback<List<Service>>() {
            @Override
            public void onResponse(Call<List<Service>> call, Response<List<Service>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("No se pudo cargar los servicios (codigo " + response.code() + ").");
                }
            }

            @Override
            public void onFailure(Call<List<Service>> call, Throwable throwable) {
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexión e inténtalo de nuevo.");
            }
        });
     }
}
