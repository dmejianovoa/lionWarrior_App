package com.damianmejia.lionwarrior.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.damianmejia.lionwarrior.R;
import com.damianmejia.lionwarrior.ui.fragments.AppointmentsFragment;
import com.damianmejia.lionwarrior.ui.fragments.BarbersFragment;
import com.damianmejia.lionwarrior.ui.fragments.ProfileFragment;
import com.damianmejia.lionwarrior.ui.fragments.ServicesFragment;
import com.damianmejia.lionwarrior.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Pantalla principal del cliente: barra inferior que cambia entre las 4 secciones
public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceSaved) {
       super.onCreate(savedInstanceSaved);

       // Protección: sin sesión no se puede estar aquí, se vuelve a la bienvenida
       SessionManager session = new SessionManager(this);
       if (!session.isLoggedIn()) {
           startActivity(new Intent(this, WelcomeActivity.class));
           finish();
           return;
       }

       EdgeToEdge.enable(this);
       setContentView(R.layout.activity_main);

       // Deja espacio arriba para la barra de estado (la barra inferior maneja su propio espacio)
       View root = findViewById(R.id.main);
       ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
           Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           v.setPadding(bars.left, bars.top, bars.right, 0);
           return insets;
       });

       BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
       bottomNav.setOnItemSelectedListener(item -> {
           int id = item.getItemId();
           if (id == R.id.nav_services) {
               showFragment(new ServicesFragment());
           } else if (id == R.id.nav_barbers) {
               showFragment(new BarbersFragment());
           } else if (id == R.id.nav_appointments) {
               showFragment(new AppointmentsFragment());
           } else if (id == R.id.nav_profile) {
               showFragment(new ProfileFragment());
           }
           return true; // true = marca la pestalla como seleccionada
       });

       // Solo la primera vez: al girar la pantalla Android restaura el fragment solo
       if (savedInstanceSaved == null) {
           bottomNav.setSelectedItemId(R.id.nav_services);
       }
   }

    // Reemplaza el fragment que se ve en el contenedor
    private void showFragment(Fragment fragment) {
       getSupportFragmentManager()
               .beginTransaction()
               .replace(R.id.fragmentContainer, fragment)
               .commit();
    }
}