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
import com.damianmejia.lionwarrior.model.RegisterRequest;
import com.damianmejia.lionwarrior.repository.AuthCallback;
import com.damianmejia.lionwarrior.repository.AuthRepository;
import com.damianmejia.lionwarrior.util.Validator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// Pantalla de registro: valida los datos del cliente y lo crea en la API
public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout tilName, tilLastName, tilEmail, tilPhone, tilPassword, tilConfirmPassword;
    private TextInputEditText etName, etLastName, etEmail, etPhone, etPassword, etConfirmPassword;
    private MaterialButton btnRegister;
    private ProgressBar progressBar;

    private AuthRepository authRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tilName = findViewById(R.id.tilName);
        tilLastName = findViewById(R.id.tilLastName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPhone = findViewById(R.id.tilPhone);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        TextView tvGoLogin = findViewById(R.id.tvGoLogin);

        authRepository = new AuthRepository();

        btnRegister.setOnClickListener(v -> attemptRegister());
        tvGoLogin.setOnClickListener(v -> goToLogin());
    }

    // Valida todos los campos y, si están bien, registra al cliente en la API
    private void attemptRegister() {
        String name = getText(etName);
        String lastName = getText(etLastName);
        String email = getText(etEmail);
        String phone = getText(etPhone);
        String password = getRawText(etPassword);
        String confirmPassword = getRawText(etConfirmPassword);

        clearErrors();

        boolean valid = true;
        if (!Validator.isNotEmpty(name)) {
            tilName.setError(getString(R.string.error_name));
            valid = false;
        }
        if (!Validator.isNotEmpty(lastName)) {
            tilLastName.setError(getString(R.string.error_lastname));
            valid = false;
        }
        if (!Validator.isValidEmail(email)) {
            tilEmail.setError(getString(R.string.error_email));
            valid = false;
        }
        if (!Validator.isValidPhone(phone)) {
            tilPhone.setError(getString(R.string.error_phone));
            valid = false;
        }
        // En el registro sí se aplican las reglas completas de la contraseña
        if (!Validator.isValidPassword(password)) {
            tilPassword.setError(getString(R.string.error_password_rules));
            valid = false;
        }
        if (!password.equals(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.error_password_match));
            valid = false;
        }
        if (!valid) return;

        RegisterRequest request = new RegisterRequest(name, lastName, email, phone, password);

        setLoading(true);
        authRepository.register(request, new AuthCallback() {
            @Override
            public void onSuccess(AuthResponse response) {
                setLoading(false);
                Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_LONG).show();
                goToLogin();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                // Ej.: "Ya existe un usuario con ese email" (409)
                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Abre el login reutilizando la pantalla si ya estaba abierta, y cierra el registro
    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    // Quita los mensajes de error de todos los campos
    private void clearErrors() {
        tilName.setError(null);
        tilLastName.setError(null);
        tilEmail.setError(null);
        tilPhone.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);
    }

    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!loading);
    }

    // Texto sin espacios al inicio y al final (nombres, correo, teléfono)
    private String getText(TextInputEditText field) {
        return field.getText() != null ? field.getText().toString().trim() : "";
    }

    // Texto tal cual se escribió (contraseñas: un espacio puede ser parte de ella)
    private String getRawText(TextInputEditText field) {
        return field.getText() != null ? field.getText().toString() : "";
    }
}