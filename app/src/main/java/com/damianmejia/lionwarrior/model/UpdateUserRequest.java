package com.damianmejia.lionwarrior.model;

// Datos que se envían para actualizar el perfil (la contraseña no se edita aquí - temporal)
public class UpdateUserRequest {
    private final String userName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;

    public UpdateUserRequest(String userName, String lastName, String email, String phoneNumber) {
        this.userName = userName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
