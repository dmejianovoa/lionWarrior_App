package com.damianmejia.lionwarrior.model;

//Cuerpo (body) que se envia al endpoint de login
public class LoginRequest {

    private final String email;
    private final String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
