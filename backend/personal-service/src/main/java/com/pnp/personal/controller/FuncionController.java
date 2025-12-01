package com.pnp.personal.controller;

import com.pnp.personal.model.FuncionPolicial;
import com.pnp.personal.service.FuncionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de gestión de funciones policiales
 */
@RestController
@RequestMapping("/api/funciones")
@RequiredArgsConstructor
public class FuncionController {

    private final FuncionService funcionService;

    /**
     * Obtiene funciones de un personal
     */
    @GetMapping("/personal/{personalId}")
    public ResponseEntity<List<FuncionPolicial>> getFuncionesByPersonalId(@PathVariable Long personalId) {
        List<FuncionPolicial> funciones = funcionService.getFuncionesByPersonalId(personalId);
        return ResponseEntity.ok(funciones);
    }

    /**
     * Obtiene funciones activas de un personal
     */
    @GetMapping("/personal/{personalId}/activas")
    public ResponseEntity<List<FuncionPolicial>> getFuncionesActivasByPersonalId(@PathVariable Long personalId) {
        List<FuncionPolicial> funciones = funcionService.getFuncionesActivasByPersonalId(personalId);
        return ResponseEntity.ok(funciones);
    }

    /**
     * Crea nueva función
     */
    @PostMapping("/personal/{personalId}")
    public ResponseEntity<?> createFuncion(@PathVariable Long personalId,
                                          @Valid @RequestBody FuncionPolicial funcion) {
        try {
            FuncionPolicial nuevaFuncion = funcionService.createFuncion(personalId, funcion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFuncion);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Actualiza función existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFuncion(@PathVariable Long id,
                                          @Valid @RequestBody FuncionPolicial funcion) {
        try {
            FuncionPolicial funcionActualizada = funcionService.updateFuncion(id, funcion);
            return ResponseEntity.ok(funcionActualizada);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Elimina función
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFuncion(@PathVariable Long id) {
        try {
            funcionService.deleteFuncion(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Función eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}
