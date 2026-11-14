package com.damianmejia.lionwarrior.model;

import java.util.List;
public class Appointment {


    private int idAppointment;
    private int idUser;
    private String userName;
    private int idBarber;
    private String barberName;
    private String appointmentDate;   // "2026-11-20"
    private String appointmentTime;   // "10:30:00"
    private String status;            // "pending", ...
    private String createdAt;
    private List<AppointmentItem> services;
    private double total;

    public int getIdAppointment() {
        return idAppointment;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUserName() {
        return userName;
    }

    public int getIdBarber() {
        return idBarber;
    }

    public String getBarberName() {
        return barberName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<AppointmentItem> getServices() {
        return services;
    }

    public double getTotal() {
        return total;
    }
}
