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
import com.damianmejia.lionwarrior.model.Service;
import com.damianmejia.lionwarrior.repository.DataCallback;
import com.damianmejia.lionwarrior.repository.ServiceRepository;
import com.damianmejia.lionwarrior.ui.adapters.ServiceAdapter;

import java.util.List;

// Sección "Servicios": muestra la lista de servicios de la barbería desde la API
public class ServicesFragment extends Fragment {


    private ProgressBar progressBar;
    private TextView tvError;
    private ServiceAdapter adapter;


    public ServicesFragment() {
        super(R.layout.fragment_services);
    }

    // Se ejecuta cuando la vista del fragment ya está creada
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        RecyclerView rvServices = view.findViewById(R.id.rvServices);
        rvServices.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ServiceAdapter();
        rvServices.setAdapter(adapter);

        loadServices();
    }

        private void loadServices() {
            progressBar.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.GONE);

            new ServiceRepository().getServices(new DataCallback<List<Service>>() {
                @Override
                public void onSuccess(List<Service> data) {
                    // Si el usuario ya cambió de pestaña, el fragment no está visible: no se toca la vista
                    if (!isAdded()) return;
                    progressBar.setVisibility(View.GONE);
                    adapter.setServices(data);
                }

                @Override
                public void onError(String message) {
                    if (!isAdded()) return;
                    progressBar.setVisibility(View.GONE);
                    tvError.setText(message);
                    tvError.setVisibility(View.VISIBLE);
                }
            });
        }
}