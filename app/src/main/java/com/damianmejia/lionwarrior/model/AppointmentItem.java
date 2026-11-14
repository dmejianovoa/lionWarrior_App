package com.damianmejia.lionwarrior.model;

// Servicio incluido en una cita (con el precio que tenía al agendar)
public class AppointmentItem {

    private int idService;
    private String serviceName;
    private double subTotal;

    public int getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getSubTotal() {
        return subTotal;
    }
}
