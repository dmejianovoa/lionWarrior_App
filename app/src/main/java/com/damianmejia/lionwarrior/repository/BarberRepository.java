package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.Barber;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class BarberRepository {

    private final ApiService apiService;

    public BarberRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    // Constructor para pruebas unitarias con un ApiService simulado
    public BarberRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public void getActiveBarbers(DataCallback<List<Barber>> callback) {
        apiService.getBarbers().enqueue(new Callback<List<Barber>>() {
            @Override
            public void onResponse(Call<List<Barber>> call, Response<List<Barber>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(filterActive(response.body()));
                } else {
                    callback.onError("No se pudo cargar los barberos (código " + response.code() + ").");
                }
            }

            @Override
            public void onFailure(Call<List<Barber>> call, Throwable t) {
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexión e inténtalo de nuevo.");
            }
        });
    }

    // El cliente solo debe ver los barberos que están trabajando
    static List<Barber> filterActive(List<Barber> barbers) {
        List<Barber> active = new ArrayList<>();
        for (Barber barber : barbers) {
            if (barber.isActive()) {
                active.add(barber);
            };
        }
        return active;
    }
}
