package org.example.evaluacion.controller;

import org.example.evaluacion.model.Vehiculo;
import org.example.evaluacion.repository.VehiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    private final VehiculoRepository repo;

    public VehiculoController(VehiculoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Vehiculo> crear(@RequestBody Vehiculo v) {
        Vehiculo creado = repo.save(v);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> listar() {
        List<Vehiculo> vehiculos = repo.findAll();
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/categoria-disponibles")
    public ResponseEntity<List<Vehiculo>> porCategoriaYDisponibles(
            @RequestParam String categoria,
            @RequestParam Integer minUnidades) {

        List<Vehiculo> vehiculos = repo.findByCategoriaAndUnidadesDisponiblesGreaterThan(
                categoria, minUnidades
        );
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/precio-rango")
    public ResponseEntity<List<Vehiculo>> porPrecioRango(
            @RequestParam Double min,
            @RequestParam Double max) {

        List<Vehiculo> vehiculos = repo.findByPrecioPorDiaBetweenOrderByPrecioPorDiaAsc(min, max);
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/buscar-modelo")
    public ResponseEntity<List<Vehiculo>> buscarModelo(@RequestParam String modelo) {
        List<Vehiculo> vehiculos = repo.findByModeloContainingIgnoreCase(modelo);
        return ResponseEntity.ok(vehiculos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> obtener(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> actualizar(@PathVariable Long id, @RequestBody Vehiculo v) {
        return repo.findById(id)
                .map(vehiculoExistente -> {
                    v.setId(id);
                    Vehiculo actualizado = repo.save(v);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}