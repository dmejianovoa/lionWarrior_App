package com.damianmejia.lionwarrior.model;

// Representa un servicio de la barbería tal como lo devuelve la API
public class Service {

    private int idService;
    private String serviceName;
    private String serviceDescription;
    private double servicePrice;
    private int durationMin;

    public int getIdService() {
        return idService;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public int getDurationMin() {
        return durationMin;
    }
}
