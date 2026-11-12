package com.damianmejia.lionwarrior.model;

public class User {

    // Representa al usuario que devuelve la API (sin contraseña)
    private int idUser;
    private String userName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String userRole;

    //Getters - Returns
    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserRole() {
        return userRole;
    }
}
