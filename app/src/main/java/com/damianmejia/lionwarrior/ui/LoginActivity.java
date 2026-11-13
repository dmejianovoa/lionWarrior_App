package com.damianmejia.lionwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.AuthResponse;
import com.damianmejia.lionwarrior.model.User;
import com.damianmejia.lionwarrior.repository.AuthCallback;
import com.damianmejia.lionwarrior.repository.AuthRepository;
import com.damianmejia.lionwarrior.util.SessionManager;
import com.damianmejia.lionwarrior.util.Validator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// Pantalla de inicio de sesión: valida los campos y autentica contra la API
public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilEmail, tilPassword;
    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private ProgressBar progressBar;

    private AuthRepository authRepository;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        TextView tvGoRegister = findViewById(R.id.tvGoRegister);

        authRepository = new AuthRepository();
        session = new SessionManager(this);

        btnLogin.setOnClickListener(v -> attemptLogin());
        // TODO: abrir RegisterActivity (siguiente paso)
        tvGoRegister.setOnClickListener(v -> { });
    }

    // Valida los campos y, si están bien, llama a la API
    private void attemptLogin() {
        String email = getText(etEmail);
        String password = etPassword.getText() != null ? etPassword.getText().toString() : "";

        // Limpia errores anteriores
        tilEmail.setError(null);
        tilPassword.setError(null);

        boolean valid = true;
        if (!Validator.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_email));
            valid = false;
        }
        // En el login solo se verifica que no esté vacía (no se revelan las reglas)
        if (!Validator.isNotEmpty(password)) {
            tilPassword.setError(getString(R.string.error_password_empty));
            valid = false;
        }
        if (!valid) return;

        setLoading(true);
        authRepository.login(email, password, new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                setLoading(false);
                User user = response.getUser();

                // La app móvil es solo para clientes
                if (user == null || !"client".equals(user.getUserRole())) {
                    Toast.makeText(LoginActivity.this, R.string.error_only_clients, Toast.LENGTH_LONG).show();
                    return;
                }

                session.saveSession(user);

                // Abre la pantalla principal y limpia el historial (atrás no vuelve al login)
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Muestra u oculta el indicador de carga y bloquea el botón para evitar doble envío
    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!loading);
    }

    // Obtiene el texto de un campo sin espacios al inicio y al final
    private String getText(TextInputEditText field) {
        return field.getText() != null ? field.getText().toString().trim() : "";
    }


}