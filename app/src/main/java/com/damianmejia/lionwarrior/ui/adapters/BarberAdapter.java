package com.damianmejia.lionwarrior.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.Barber;

import java.util.ArrayList;
import java.util.List;

// Patrón Adapter: convierte cada Barber en una tarjeta visible en el RecyclerView
public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.BarberViewHolder> {

    private final List<Barber> barbers = new ArrayList<>();

    public void setBarbers(List<Barber> newBarbers) {
        barbers.clear();
        barbers.addAll(newBarbers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_barber, parent, false);
        return new BarberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarberViewHolder holder, int position) {
        Barber barber = barbers.get(position);
        holder.tvName.setText(barber.getFullName());
        holder.tvSpecialty.setText(barber.getSpecialty());
        holder.tvSchedule.setText(barber.getAvailableSchedule());
    }

    @Override
    public int getItemCount() {
        return barbers.size();
    }

    static class BarberViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvSpecialty, tvSchedule;

        BarberViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBarberName);
            tvSpecialty = itemView.findViewById(R.id.tvBarberSpecialty);
            tvSchedule = itemView.findViewById(R.id.tvBarberSchedule);
        }

    }

}
