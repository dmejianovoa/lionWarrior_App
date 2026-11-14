package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.Appointment;
import com.damianmejia.lionwarrior.network.ApiService;
import com.damianmejia.lionwarrior.network.RetrofitClient;
import com.damianmejia.lionwarrior.util.ApiErrorParser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Patrón Repository: obtiene las citas del cliente desde la API
public class AppointmentRepository {

    private final ApiService apiService;

    public AppointmentRepository()  {
        this.apiService = RetrofitClient.getApiService();
    }

    // Constructor para pruebas unitarias con un ApiService simulado
    public AppointmentRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    // Trae solo las citas del cliente indicado (la API filtra con ?idUser=)
    public void getClientAppointments(int idUser, DataCallback<List<Appointment>> callback) {
        apiService.getAppointments(idUser).enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(ApiErrorParser.parse(response));
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                callback.onError("No se pudo conectar con el servidor. Revisa tu conexión e inténtalo de nuevo.");
            }
        });
    }
}
