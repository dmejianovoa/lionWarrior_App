package com.damianmejia.lionwarrior.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

// Pruebas unitarias de las reglas de validación de formularios
public class ValidatorTest {

    //----------- Correo -----------
    @Test
    public void email_valido_retornaTrue() {
        assertTrue(Validator.isValidEmail("dayan@correo.com"));
    }

    @Test
    public void email_sinArroba_retornaFalse() {
        assertFalse(Validator.isValidEmail("dayancorreo.com"));
    }

    @Test
    public void email_sinDominio_retornaFalse() {
        assertFalse(Validator.isValidEmail("dayan@"));
    }

    // ---------- Teléfono (formato 300 000 0000) ----------
    @Test
    public void telefono_formatoCorrecto_retornaTrue() {
        assertTrue(Validator.isValidPhone("300 323 6573"));
    }

    @Test
    public void telefono_conGuiones_retornaFalse() {
        assertFalse(Validator.isValidPhone("300-323-6573"));
    }

    @Test
    public void telefono_sinEspacios_retornaFalse() {
        assertFalse(Validator.isValidPhone("3003236573"));
    }

    @Test
    public void telefono_noEmpiezaPor3_retornaFalse() {
        assertFalse(Validator.isValidPhone("200 323 6573"));
    }

    // ---------- Contraseña (8+ caracteres, letra y número) ----------
    @Test
    public void password_valida_retornaTrue() {
        assertTrue(Validator.isValidPassword("Clave2006"));
    }

    @Test
    public void password_muyCorta_retornaFalse() {
        assertFalse(Validator.isValidPassword("Clave1"));
    }

    @Test
    public void password_sinNumero_retornaFalse() {
        assertFalse(Validator.isValidPassword("ClaveSegura"));
    }

    @Test
    public void password_sinLetra_retornaFalse() {
        assertFalse(Validator.isValidPassword("12345678"));
    }

    // ---------- Campo obligatorio ----------
    @Test
    public void texto_conContenido_retornaTrue() {
        assertTrue(Validator.isNotEmpty("Damian"));
    }

    @Test
    public void texto_vacio_retornaFalse() {
        assertFalse(Validator.isNotEmpty(""));
    }

}

