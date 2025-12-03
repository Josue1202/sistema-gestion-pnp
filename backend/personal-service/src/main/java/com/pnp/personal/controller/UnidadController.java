package com.pnp.personal.controller;

import com.pnp.personal.model.UnidadPolicial;
import com.pnp.personal.service.UnidadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/unidades")
public class UnidadController {

    private final UnidadService unidadService;

    public UnidadController(UnidadService unidadService) {
        this.unidadService = unidadService;
    }

    @GetMapping
    public ResponseEntity<List<UnidadPolicial>> getAll() {
        return ResponseEntity.ok(unidadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadPolicial> getById(@PathVariable Long id) {
        return ResponseEntity.ok(unidadService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UnidadPolicial> create(@Valid @RequestBody UnidadPolicial unidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadService.create(unidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadPolicial> update(@PathVariable Long id, @Valid @RequestBody UnidadPolicial unidad) {
        return ResponseEntity.ok(unidadService.update(id, unidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        unidadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
