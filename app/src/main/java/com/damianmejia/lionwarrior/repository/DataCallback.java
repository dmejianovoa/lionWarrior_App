package com.damianmejia.lionwarrior.repository;

// Callback genérico: T es el tipo de dato que se espera (lista de servicios, barberos, etc.)
public interface DataCallback<T> {
    void onSuccess(T data);
    void onError(String message);
}
