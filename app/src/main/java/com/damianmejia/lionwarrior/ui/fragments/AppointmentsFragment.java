package com.damianmejia.lionwarrior.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.Appointment;
import com.damianmejia.lionwarrior.repository.AppointmentRepository;
import com.damianmejia.lionwarrior.repository.DataCallback;
import com.damianmejia.lionwarrior.ui.adapters.AppointmentAdapter;
import com.damianmejia.lionwarrior.util.SessionManager;

import java.util.List;

// Sección "Mis citas": muestra las citas del cliente que inició sesión
public class AppointmentsFragment extends Fragment {

    private ProgressBar progressBar;
    private TextView tvError;
    private AppointmentAdapter adapter;

    public AppointmentsFragment() {
        super(R.layout.fragment_appointments);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        RecyclerView rvAppointments = view.findViewById(R.id.rvAppointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new AppointmentAdapter();
        rvAppointments.setAdapter(adapter);

        loadAppointments();
    }

    private void loadAppointments() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        // El id sale de la sesión: cada cliente solo consulta sus propias citas
        int idUser = new SessionManager(requireContext()).getIdUser();

        new AppointmentRepository().getClientAppointments(idUser, new DataCallback<List<Appointment>>() {
            @Override
            public void onSuccess(List<Appointment> data) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                if (data.isEmpty()) {
                    showMessage(getString(R.string.appointments_empty));
                    return;
                }
                adapter.setAppointments(data);
            }

            @Override
            public void onError(String message) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                showMessage(message);
            }
        });
    }

    private void showMessage(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}