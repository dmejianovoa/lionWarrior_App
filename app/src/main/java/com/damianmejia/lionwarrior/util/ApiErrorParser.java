package com.damianmejia.lionwarrior.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Response;

// Lee el mensaje de error que envía la API ({"mensaje": "..."}) en cualquier respuesta fallida
public final class ApiErrorParser {

    private ApiErrorParser() { } // Clase de utilidades: no se instancia

    public static String parse(Response<?> response) {
        String fallback = "Ocurrió un error inesperado (código " + response.code() + ").";
        try {
            if (response.errorBody() == null) return fallback;
            JsonObject json = JsonParser.parseString(response.errorBody().string()).getAsJsonObject();
            if (json.has("mensaje")) {
                return json.get("mensaje").getAsString();
            }
        } catch (Exception e) {
            // Si el cuerpo no es JSON o no tiene "mensaje", se usa el mensaje genérico
        }
        return fallback;
    }
}