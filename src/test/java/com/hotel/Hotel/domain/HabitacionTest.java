package com.hotel.Hotel.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para las entidades de Habitación
 * Verifica el comportamiento de estados y validaciones de habitaciones
 * 
 * @author Test creado para validación de lógica de negocio
 */
@DisplayName("Pruebas de Habitación")
class HabitacionTest {

    @Test
    @DisplayName("Debe crear habitación estándar con estado disponible")
    void debeCrearHabitacionEstandarConEstadoDisponible() {
        // Arrange & Act
        HabitacionEstandar habitacion = new HabitacionEstandar("101", 2, 150.0, 2);

        // Assert
        assertNotNull(habitacion.getId(), "El ID no debe ser nulo");
        assertEquals("101", habitacion.getNumero(), "El número debe coincidir");
        assertEquals(2, habitacion.getCapacidadMaxima(), "La capacidad debe ser 2");
        assertEquals(150.0, habitacion.getPrecioPorNoche(), "El precio debe coincidir");
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), "Debe estar disponible");
        assertEquals(2, habitacion.getCamasIndividuales(), "Debe tener 2 camas individuales");
    }

    @Test
    @DisplayName("Debe crear suite presidencial con amenidades")
    void debeCrearSuitePresidencialConAmenidades() {
        // Arrange & Act
        SuitePresidencial suite = new SuitePresidencial("P01", 4, 500.0, true, true);

        // Assert
        assertNotNull(suite.getId(), "El ID no debe ser nulo");
        assertEquals("P01", suite.getNumero(), "El número debe coincidir");
        assertEquals(4, suite.getCapacidadMaxima(), "La capacidad debe ser 4");
        assertEquals(500.0, suite.getPrecioPorNoche(), "El precio debe coincidir");
        assertTrue(suite.isIncluyeMayordomo(), "Debe incluir mayordomo");
        assertTrue(suite.isJacuzziPrivado(), "Debe incluir jacuzzi privado");
        assertEquals(EstadoHabitacion.DISPONIBLE, suite.getEstado(), "Debe estar disponible");
    }

    @Test
    @DisplayName("Debe cambiar estado a OCUPADA al asignar a reserva")
    void debeCambiarEstadoAOcupadaAlAsignarAReserva() {
        // Arrange
        HabitacionEstandar habitacion = new HabitacionEstandar("102", 2, 150.0, 2);

        // Act
        habitacion.asignarAReserva();

        // Assert
        assertEquals(EstadoHabitacion.OCUPADA, habitacion.getEstado(), "Debe estar ocupada");
    }

    @Test
    @DisplayName("Debe lanzar excepción al asignar habitación ocupada")
    void debeLanzarExcepcionAlAsignarHabitacionOcupada() {
        // Arrange
        HabitacionEstandar habitacion = new HabitacionEstandar("103", 2, 150.0, 2);
        habitacion.asignarAReserva();

        // Act & Assert
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> habitacion.asignarAReserva(),
            "Debe lanzar IllegalStateException"
        );
        
        assertEquals("La habitación no está disponible", exception.getMessage());
    }

    @Test
    @DisplayName("Debe habilitar habitación ocupada")
    void debeHabilitarHabitacionOcupada() {
        // Arrange
        HabitacionEstandar habitacion = new HabitacionEstandar("104", 2, 150.0, 2);
        habitacion.asignarAReserva();

        // Act
        habitacion.habilitar();

        // Assert
        assertEquals(EstadoHabitacion.DISPONIBLE, habitacion.getEstado(), "Debe estar disponible");
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con número vacío")
    void debeLanzarExcepcionAlCrearHabitacionConNumeroVacio() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new HabitacionEstandar("", 2, 150.0, 2),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("Número obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con capacidad inválida")
    void debeLanzarExcepcionAlCrearHabitacionConCapacidadInvalida() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new HabitacionEstandar("105", 0, 150.0, 2),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("Capacidad mínima: 1", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear habitación con precio negativo")
    void debeLanzarExcepcionAlCrearHabitacionConPrecioNegativo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new HabitacionEstandar("106", 2, -50.0, 2),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("Precio no puede ser negativo", exception.getMessage());
    }
}
