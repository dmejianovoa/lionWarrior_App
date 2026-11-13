package com.damianmejia.lionwarrior.util;

import java.util.regex.Pattern;

// Reglas de validación reutilizables (Login, Registro y Editar perfil)
public final class Validator {

    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[\\w.]+$");
    // Formato de teléfono: 300 000 0000
    private static final Pattern PHONE = Pattern.compile("^3\\d{2} \\d{3} \\d{4}$");
    // Mínimo 8 caracteres, con al menos una letra y un número
    private static final Pattern PASSWORD = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{8,}$");


    // Clase de utilidades: no se instancia
    private Validator() {}

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE.matcher(phone).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD.matcher(password).matches();
    }

    public static boolean isNotEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }
 }
