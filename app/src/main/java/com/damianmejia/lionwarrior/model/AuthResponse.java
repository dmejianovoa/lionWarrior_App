package com.damianmejia.lionwarrior.model;

// Respuesta de Login: mensaje de la API + datos del usuario
public class AuthResponse {

    private String message;
    private User user;

    public String getMessage() { return message;}
    public User getUser() { return user;}
}
