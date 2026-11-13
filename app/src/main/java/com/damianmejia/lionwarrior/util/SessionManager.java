package com.damianmejia.lionwarrior.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.damianmejia.lionwarrior.model.User;

// guarda y consulta la sesion del cliente en el almacenamiento privado de la app
public class SessionManager {

    private static final String PREFS_NAME = "lion_warrior_session";
    private static final String KEY_ID_USER = "id_user";
    private static final String KEY_USER_NAME = "user_name";

    private final SharedPreferences prefs;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    //Guarda los datos basicos del cliente despues de iniciar sesión
    public void saveSession(User user) {
        prefs.edit()
                .putInt(KEY_ID_USER, user.getIdUser())
                .putString(KEY_USER_NAME, user.getUserName())
                .apply();
    }

    //Hay una sesion si existe un id guardado
    public boolean isLoggedIn() {
        return prefs.getInt(KEY_ID_USER, -1) != -1;
    }

     public int getIdUser() { return prefs.getInt(KEY_ID_USER, -1);}

    public String getUserName() { return prefs.getString(KEY_USER_NAME, "");}

    //Borra toda al cerrar sesion
    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
