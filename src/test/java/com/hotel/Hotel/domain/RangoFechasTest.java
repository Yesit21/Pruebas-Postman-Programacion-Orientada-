package com.hotel.Hotel.domain;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para el Value Object RangoFechas
 * Verifica validaciones y cálculo de días
 * 
 * @author Test creado para validación de lógica de negocio
 */
@DisplayName("Pruebas de RangoFechas")
class RangoFechasTest {

    @Test
    @DisplayName("Debe crear rango de fechas válido")
    void debeCrearRangoFechasValido() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 10, 20, 12, 0);

        // Act
        RangoFechas rango = new RangoFechas(inicio, fin);

        // Assert
        assertEquals(inicio, rango.fechaInicio(), "La fecha de inicio debe coincidir");
        assertEquals(fin, rango.fechaFin(), "La fecha de fin debe coincidir");
    }

    @Test
    @DisplayName("Debe calcular días correctamente")
    void debeCalcularDiasCorrectamente() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 10, 20, 14, 0);

        // Act
        RangoFechas rango = new RangoFechas(inicio, fin);

        // Assert
        assertEquals(5, rango.getDias(), "Debe calcular 5 días de diferencia");
    }

    @Test
    @DisplayName("Debe calcular cero días para mismo día")
    void debeCalcularCeroDiasParaMismoDia() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 15, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 10, 15, 18, 0);

        // Act
        RangoFechas rango = new RangoFechas(inicio, fin);

        // Assert
        assertEquals(0, rango.getDias(), "Debe calcular 0 días para el mismo día");
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha fin es anterior a fecha inicio")
    void debeLanzarExcepcionCuandoFechaFinEsAnteriorAFechaInicio() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 20, 14, 0);
        LocalDateTime fin = LocalDateTime.of(2026, 10, 15, 14, 0);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RangoFechas(inicio, fin),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("La fecha fin no puede ser anterior a la de inicio", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha inicio es nula")
    void debeLanzarExcepcionCuandoFechaInicioEsNula() {
        // Arrange
        LocalDateTime fin = LocalDateTime.of(2026, 10, 20, 14, 0);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RangoFechas(null, fin),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("Las fechas no pueden ser nulas", exception.getMessage());
    }

    @Test
    @DisplayName("Debe lanzar excepción cuando fecha fin es nula")
    void debeLanzarExcepcionCuandoFechaFinEsNula() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2026, 10, 15, 14, 0);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new RangoFechas(inicio, null),
            "Debe lanzar IllegalArgumentException"
        );
        
        assertEquals("Las fechas no pueden ser nulas", exception.getMessage());
    }

    @Test
    @DisplayName("Debe permitir fecha inicio igual a fecha fin")
    void debePermitirFechaInicioIgualAFechaFin() {
        // Arrange
        LocalDateTime fecha = LocalDateTime.of(2026, 10, 15, 14, 0);

        // Act
        RangoFechas rango = new RangoFechas(fecha, fecha);

        // Assert
        assertEquals(fecha, rango.fechaInicio(), "La fecha de inicio debe coincidir");
        assertEquals(fecha, rango.fechaFin(), "La fecha de fin debe coincidir");
        assertEquals(0, rango.getDias(), "Debe calcular 0 días");
    }
}
