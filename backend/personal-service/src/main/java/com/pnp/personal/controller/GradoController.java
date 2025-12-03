package com.pnp.personal.controller;

import com.pnp.personal.model.GradoPolicial;
import com.pnp.personal.service.GradoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grados")
public class GradoController {

    private final GradoService gradoService;

    public GradoController(GradoService gradoService) {
        this.gradoService = gradoService;
    }

    @GetMapping
    public ResponseEntity<List<GradoPolicial>> getAll() {
        return ResponseEntity.ok(gradoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GradoPolicial> getById(@PathVariable Long id) {
        return ResponseEntity.ok(gradoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<GradoPolicial> create(@Valid @RequestBody GradoPolicial grado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gradoService.create(grado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradoPolicial> update(@PathVariable Long id, @Valid @RequestBody GradoPolicial grado) {
        return ResponseEntity.ok(gradoService.update(id, grado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gradoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
