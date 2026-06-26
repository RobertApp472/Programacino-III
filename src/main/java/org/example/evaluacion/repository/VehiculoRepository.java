package org.example.evaluacion.repository;

import org.example.evaluacion.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    // a) categoría + unidades disponibles
    List<Vehiculo> findByCategoriaAndUnidadesDisponiblesGreaterThan(
            String categoria, Integer unidadesDisponibles);

    // b) rango de precio ordenado ascendente
    List<Vehiculo> findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(
            Double min, Double max);

    // c) búsqueda parcial de modelo
    List<Vehiculo> findByModeloContainingIgnoreCase(String modelo);
}