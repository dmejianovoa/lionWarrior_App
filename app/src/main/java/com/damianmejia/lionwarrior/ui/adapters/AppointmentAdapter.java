package com.damianmejia.lionwarrior.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.model.Appointment;
import com.damianmejia.lionwarrior.model.AppointmentItem;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Patrón Adapter: convierte cada cita en una tarjeta visible en el RecyclerView
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {

    private static final Locale CO = new Locale("es", "CO");

    private final List<Appointment> appointments = new ArrayList<>();
    private final NumberFormat priceFormat = NumberFormat.getCurrencyInstance(CO);
    // "2026-11-20" (API) → "jue, 20 nov 2026" (pantalla)
    private final SimpleDateFormat apiDate = new SimpleDateFormat("yyyy-MM-dd", CO);
    private final SimpleDateFormat displayDate = new SimpleDateFormat("EEE, d MMM yyyy", CO);


    public AppointmentAdapter() {
        priceFormat.setMaximumFractionDigits(0);
    }

    public void setAppointments(List<Appointment> newAppointments) {
        appointments.clear();
        appointments.addAll(newAppointments);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return  new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        Context context = holder.itemView.getContext();

        holder.tvDateTime.setText(formatDate(appointment.getAppointmentDate())
                + " · " + formatTime(appointment.getAppointmentTime()));
        holder.tvStatus.setText(getStatusLabel(context, appointment.getStatus()));
        holder.tvBarber.setText("Barbero: " + appointment.getBarberName());
        holder.tvServices.setText(joinServiceNames(appointment.getServices()));
        holder.tvTotal.setText("Total: " + priceFormat.format(appointment.getTotal()));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    // Si la fecha no viene en el formato esperado, se muestra tal cual
    private String formatDate(String date) {
        try {
            Date parsed = apiDate.parse(date);
            return parsed != null ? displayDate.format(parsed) : date;
        } catch (ParseException e) {
            return date;
        }
    }

    // "10:30:00" → "10:30"
    private String formatTime(String time) {
        return time != null && time.length() >= 5 ? time.substring(0, 5) : time;
    }

    // Traduce el estado de la API a un texto en español
    private String getStatusLabel(Context context, String status) {
        if (status == null) return "";
        switch (status) {
            case "pending":   return context.getString(R.string.status_pending);
            case "confirmed": return context.getString(R.string.status_confirmed);
            case "completed": return context.getString(R.string.status_completed);
            case "cancelled": return context.getString(R.string.status_cancelled);
            default:          return status;
        }
    }

    // Une los nombres: "Corte Desvanecido, Barba"
    private String joinServiceNames(List<AppointmentItem> services) {
        if (services == null || services.isEmpty()) return "";
        List<String> names = new ArrayList<>();
        for (AppointmentItem item : services) {
            names.add(item.getServiceName());
        }
        return String.join(", ", names);
    }

    static class AppointmentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDateTime, tvStatus, tvBarber, tvServices, tvTotal;

        AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvBarber = itemView.findViewById(R.id.tvBarber);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }

}
