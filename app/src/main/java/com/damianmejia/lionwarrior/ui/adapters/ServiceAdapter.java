package com.damianmejia.lionwarrior.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// Patrón Adapter: convierte cada Service en una tarjeta visible en el RecyclerView
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>{

    private final List<Service> services = new ArrayList<>();
    // Formato de pesos colombianos sin decimales: $ 17.000
    private final NumberFormat priceFormat = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

    public ServiceAdapter() {
        priceFormat.setMaximumFractionDigits(0);
    }

    // Reemplaza la lista y redibuja
    public void setServices(List<Service> newServices) {
        services.clear();
        services.addAll(newServices);
        notifyDataSetChanged();
    }

    // Crea una tarjeta nueva (solo cuando no hay una para reciclar)
    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    // Llena una tarjeta con los datos del servicio en esa posición
    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.tvName.setText(service.getServiceName());
        holder.tvDescription.setText(service.getServiceDescription());
        holder.tvPrice.setText(priceFormat.format(service.getServicePrice()));
        holder.tvDuration.setText(service.getDurationMin() + "Min");
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    // Guarda las referencias a los elementos de una tarjeta
    static class ServiceViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvDescription, tvPrice, tvDuration;

        ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvServiceName);
            tvDescription = itemView.findViewById(R.id.tvServiceDescription);
            tvPrice = itemView.findViewById(R.id.tvServicePrice);
            tvDuration = itemView.findViewById(R.id.tvServiceDuration);
        }
    }

}
