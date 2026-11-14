package com.damianmejia.lionwarrior.repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.damianmejia.lionwarrior.model.AuthResponse;
import com.damianmejia.lionwarrior.network.ApiService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Pruebas del repositorio de autenticación con una API simulada (sin internet)
public class AuthRepositoryTest {

    private ApiService apiService;
    private Call<AuthResponse> call;
    private AuthCallback callback;
    private AuthRepository repository;

    // Se ejecuta antes de cada prueba: prepara los objetos simulados
    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        apiService = mock(ApiService.class);
        call = mock(Call.class);
        callback = mock(AuthCallback.class);
        when(apiService.login(any())).thenReturn(call);
        // Se usa el constructor de pruebas: el repositorio recibe la API falsa
        repository = new AuthRepository(apiService);
    }

    @Test
    public void login_exitoso_llamaOnSuccess() {
        AuthResponse authResponse = mock(AuthResponse.class);
        // Cuando el repositorio haga enqueue, la "API" responde 200 con authResponse
        doAnswer(invocation -> {
            Callback<AuthResponse> cb = invocation.getArgument(0);
            cb.onResponse(call, Response.success(authResponse));
            return null;
        }).when(call).enqueue(any());

        repository.login("dayan@correo.com", "Clave2006", callback);

        verify(callback).onSuccess(authResponse);
        verify(callback, never()).onError(any());
    }

    @Test
    public void login_credencialesIncorrectas_entregaMensajeDeLaApi() {
        ResponseBody errorBody = ResponseBody.create(
                "{\"mensaje\":\"Credenciales incorrectas\"}",
                MediaType.get("application/json"));
        // La "API" responde 401 con un mensaje de error
        doAnswer(invocation -> {
            Callback<AuthResponse> cb = invocation.getArgument(0);
            cb.onResponse(call, Response.error(401, errorBody));
            return null;
        }).when(call).enqueue(any());

        repository.login("dayan@correo.com", "malaClave1", callback);

        verify(callback).onError("Credenciales incorrectas");
        verify(callback, never()).onSuccess(any());
    }

    @Test
    public void login_sinConexion_entregaMensajeDeConexion() {
        // La "API" no responde: simula que no hay internet
        doAnswer(invocation -> {
            Callback<AuthResponse> cb = invocation.getArgument(0);
            cb.onFailure(call, new IOException("Sin conexión"));
            return null;
        }).when(call).enqueue(any());

        repository.login("dayan@correo.com", "Clave2006", callback);

        verify(callback).onError(contains("No se pudo conectar"));
    }
}
