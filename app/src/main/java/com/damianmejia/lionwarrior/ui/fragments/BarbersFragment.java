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
import com.damianmejia.lionwarrior.model.Barber;
import com.damianmejia.lionwarrior.repository.BarberRepository;
import com.damianmejia.lionwarrior.repository.DataCallback;
import com.damianmejia.lionwarrior.ui.adapters.BarberAdapter;

import java.util.List;

// Sección "Barberos": muestra los barberos activos de la barbería
public class BarbersFragment extends Fragment {


    private ProgressBar progressBar;
    private TextView tvError;
    private BarberAdapter adapter;


    public BarbersFragment() {
        super(R.layout.fragment_barbers);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        RecyclerView rvBarbers = view.findViewById(R.id.rvBarbers);
        rvBarbers.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new BarberAdapter();
        rvBarbers.setAdapter(adapter);

        loadBarbers();
    }

    private void loadBarbers() {
        progressBar.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);

        new BarberRepository().getActiveBarbers(new DataCallback<List<Barber>>() {
            @Override
            public void onSuccess(List<Barber> data) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                // Estado vacío: se informa en vez de dejar la pantalla en blanco
                if (data.isEmpty()) {
                    showMessage(getString(R.string.barbers_empty));
                    return;
                }
                adapter.setBarbers(data);
            }

            @Override
            public void onError(String message) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                showMessage(message);
            }
        });
    }

    // Muestra un mensaje centrado (error o lista vacía)
    private void showMessage(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }
}