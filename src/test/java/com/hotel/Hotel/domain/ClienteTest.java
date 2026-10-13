package com.hotel.Hotel.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la entidad Cliente
 * Verifica el comportamiento del sistema de penalizaciones y estado del cliente
 * 
 * @author Test creado para validación de lógica de negocio
 */
@DisplayName("Pruebas de Cliente")
class ClienteTest {

    @Test
    @DisplayName("Debe crear un cliente activo con cero penalizaciones")
    void debeCrearClienteActivoConCeroPenalizaciones() {
        // Arrange & Act
        Cliente cliente = new Cliente("Juan Pérez", "juan@email.com");

        // Assert
        assertNotNull(cliente.getId(), "El ID no debe ser nulo");
        assertEquals("Juan Pérez", cliente.getNombre(), "El nombre debe coincidir");
        assertEquals("juan@email.com", cliente.getEmail(), "El email debe coincidir");
        assertTrue(cliente.isActivo(), "El cliente debe estar activo");
        assertEquals(0, cliente.getPenalizaciones(), "Las penalizaciones deben ser 0");
        assertTrue(cliente.puedeRealizarReservas(), "El cliente debe poder realizar reservas");
    }

    @Test
    @DisplayName("Debe desactivar cliente después de 3 penalizaciones")
    void debeDesactivarClienteDespuesDeTresPenalizaciones() {
        // Arrange
        Cliente cliente = new Cliente("María González", "maria@email.com");

        // Act
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Assert
        assertEquals(3, cliente.getPenalizaciones(), "Debe tener 3 penalizaciones");
        assertFalse(cliente.isActivo(), "El cliente debe estar inactivo");
        assertFalse(cliente.puedeRealizarReservas(), "El cliente no debe poder realizar reservas");
    }

    @Test
    @DisplayName("Debe permitir reactivar un cliente penalizado")
    void debePermitirReactivarClientePenalizado() {
        // Arrange
        Cliente cliente = new Cliente("Carlos Rodríguez", "carlos@email.com");
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Act
        cliente.reactivar();

        // Assert
        assertTrue(cliente.isActivo(), "El cliente debe estar activo");
        assertEquals(0, cliente.getPenalizaciones(), "Las penalizaciones deben reiniciarse a 0");
        assertTrue(cliente.puedeRealizarReservas(), "El cliente debe poder realizar reservas");
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear cliente sin nombre")
    void debeLanzarExcepcionAlCrearClienteSinNombre() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente("", "email@email.com"),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("El nombre es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción al crear cliente sin email")
    void debeLanzarExcepcionAlCrearClienteSinEmail() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Cliente("Pedro López", ""),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("El email es obligatorio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe mantener cliente activo con menos de 3 penalizaciones")
    void debeMantenerClienteActivoConMenosDeTresPenalizaciones() {
        // Arrange
        Cliente cliente = new Cliente("Ana Martínez", "ana@email.com");

        // Act
        cliente.registrarPenalizacion();
        cliente.registrarPenalizacion();

        // Assert
        assertEquals(2, cliente.getPenalizaciones(), "Debe tener 2 penalizaciones");
        assertTrue(cliente.isActivo(), "El cliente debe seguir activo");
        assertTrue(cliente.puedeRealizarReservas(), "El cliente debe poder realizar reservas");
    }
}
