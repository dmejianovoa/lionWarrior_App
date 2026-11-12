package com.damianmejia.lionwarrior.repository;

import com.damianmejia.lionwarrior.model.AuthResponse;

// Contrato para avisarle a la pantalla el resultado de login o registro
public interface AuthCallback {
    void onSuccess(AuthResponse response);
    void onError(String message);
}
