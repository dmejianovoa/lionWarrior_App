package com.damianmejia.lionwarrior.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.UpdateUserRequest;
import com.damianmejia.lionwarrior.model.User;
import com.damianmejia.lionwarrior.repository.DataCallback;
import com.damianmejia.lionwarrior.repository.UserRepository;
import com.damianmejia.lionwarrior.ui.WelcomeActivity;
import com.damianmejia.lionwarrior.util.SessionManager;
import com.damianmejia.lionwarrior.util.Validator;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// Sección "Perfil": muestra y actualiza los datos del cliente, y permite cerrar sesión
public class ProfileFragment extends Fragment{

    private TextInputLayout tilName, tilLastName, tilEmail, tilPhone;
    private TextInputEditText etName, etLastName, etEmail, etPhone;
    private MaterialButton btnSave;
    private ProgressBar progressBar;

    private SessionManager session;
    private final UserRepository userRepository = new UserRepository();

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilName = view.findViewById(R.id.tilName);
        tilLastName = view.findViewById(R.id.tilLastName);
        tilEmail = view.findViewById(R.id.tilEmail);
        tilPhone = view.findViewById(R.id.tilPhone);
        etName = view.findViewById(R.id.etName);
        etLastName = view.findViewById(R.id.etLastName);
        etEmail = view.findViewById(R.id.etEmail);
        etPhone = view.findViewById(R.id.etPhone);
        btnSave = view.findViewById(R.id.btnSave);
        progressBar = view.findViewById(R.id.progressBar);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);

        session = new SessionManager(requireContext());

        btnSave.setOnClickListener(v -> attemptSave());
        btnLogout.setOnClickListener(v -> confirmLogout());

        loadProfile();
    }

    // Trae los datos actuales del cliente desde la API y llena los campos
    private void loadProfile() {
        setLoading(true);
        userRepository.getUser(session.getIdUser(), new DataCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (!isAdded()) return;
                setLoading(false);
                fillFields(user);
            }

            @Override
            public void onError(String message) {
                if (!isAdded()) return;
                setLoading(false);
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        });
    }

    // Valida con las mismas reglas del registro y envía los cambios
    private void attemptSave() {
        String name = getText(etName);
        String lastName = getText(etLastName);
        String email = getText(etEmail);
        String phone = getText(etPhone);

        //Campos limpios
        tilName.setError(null);
        tilLastName.setError(null);
        tilEmail.setError(null);
        tilPhone.setError(null);

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
        if (!valid) return;

        UpdateUserRequest request = new UpdateUserRequest(name, lastName, email, phone);

        setLoading(true);
        userRepository.updateUser(session.getIdUser(), request, new DataCallback<User>() {
            @Override
            public void onSuccess(User user) {
                if (!isAdded()) return;
                setLoading(false);
                fillFields(user);
                session.saveSession(user); // Actualiza el nombre guardado en la sesión
                Toast.makeText(requireContext(), R.string.profile_updated, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                if (!isAdded()) return;
                setLoading(false);
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Pide confirmación antes de cerrar sesión
    private void confirmLogout() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.logout_confirm_title)
                .setMessage(R.string.logout_confirm_message)
                .setNegativeButton(R.string.btn_cancel, null)
                .setPositiveButton(R.string.btn_logout, ((dialog, which) -> logout()))
                .show();
    }

    // Borra la sesión y vuelve a la bienvenida, limpiando todo el historial de pantallas
    private void logout() {
        session.clearSession();
        Intent intent = new Intent(requireContext(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    // Datos de llenados por el usuario
    private void fillFields(User user) {
        etName.setText(user.getUserName());
        etLastName.setText(user.getLastName());
        etEmail.setText(user.getEmail());
        etPhone.setText(user.getPhoneNumber());
    }

    //Spinner de carga / validacion Boolean
    private void setLoading(boolean loading) {
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        btnSave.setEnabled(!loading);
    }


    private String getText(TextInputEditText field) {
        return field.getText() != null ? field.getText().toString().trim() : "";
    }
}
