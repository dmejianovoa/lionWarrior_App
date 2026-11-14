package com.damianmejia.lionwarrior.model;


// Representa un barbero tal como lo devuelve la API
public class Barber {
    private int idBarber;
    private int idUser;
    private String userName;
    private String lastName;
    private boolean active;
    private String specialty;
    private String availableSchedule;

    public int getIdBarber() {
        return idBarber;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return active;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getAvailableSchedule() {
        return availableSchedule;
    }

    // Nombre completo para mostrar en pantalla
    public String getFullName() {return userName + " " + lastName; }
}
