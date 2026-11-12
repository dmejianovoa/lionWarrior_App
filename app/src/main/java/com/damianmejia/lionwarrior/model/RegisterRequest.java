package com.damianmejia.lionwarrior.model;

// Cuerpo (body) que se envia al endpoint de registro
public class RegisterRequest {

    private final String userName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String password;

    public RegisterRequest(String userName, String lastName, String email,
                           String phoneNumber, String password) {
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
