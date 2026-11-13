package com.damianmejia.lionwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.util.SessionManager;

// Primera pantalla: el cliente elige iniciar sesión o registrarse
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si ya hay sesión guardada, se salta la bienvenida y va a la pantalla principal
        SessionManager session = new SessionManager(this);
        if (session.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish(); // Cierra la bienvenida para que "atrás" no regrese aquí
            return;
        }

        setContentView(R.layout.activity_welcome);

        Button btnGoLogin = findViewById(R.id.btnGoLogin);
        Button btnGoRegister = findViewById(R.id.btnGoRegister);

        // TODO: abrir LoginActivity y RegisterActivity (siguientes pasos)
        btnGoLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        btnGoRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}