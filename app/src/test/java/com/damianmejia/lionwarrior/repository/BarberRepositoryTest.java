package com.damianmejia.lionwarrior.repository;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.damianmejia.lionwarrior.model.Barber;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Pruebas de la regla de negocio: el cliente solo ve barberos activos
public class BarberRepositoryTest {

    // Crea un barbero falso con Mockito (Barber no tiene setters)
    private Barber barber(boolean active) {
        Barber barber = mock(Barber.class);
        when(barber.isActive()).thenReturn(active);
        return barber;
    }

    @Test
    public void filterActive_dejaSoloLosActivos() {
        Barber activo1 = barber(true);
        Barber inactivo = barber(false);
        Barber activo2 = barber(true);

        List<Barber> result = BarberRepository.filterActive(Arrays.asList(activo1, inactivo, activo2));

        assertEquals(2, result.size());
        assertTrue(result.contains(activo1));
        assertTrue(result.contains(activo2));
    }

    @Test
    public void filterActive_todosInactivos_retornaListaVacia() {
        List<Barber> result = BarberRepository.filterActive(Arrays.asList(barber(false), barber(false)));
        assertTrue(result.isEmpty());
    }

    @Test
    public void filterActive_listaVacia_retornaListaVacia() {
        List<Barber> result = BarberRepository.filterActive(new ArrayList<>());
        assertTrue(result.isEmpty());
    }
}
