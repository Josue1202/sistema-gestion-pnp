package com.pnp.personal.controller;

import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.service.PersonalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de gestión de personal PNP
 */
@RestController
@RequestMapping("/api/personal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PersonalController {

    private final PersonalService personalService;

    /**
     * Obtiene todo el personal
     */
    @GetMapping
    public ResponseEntity<List<PersonalPNP>> getAllPersonal() {
        List<PersonalPNP> personal = personalService.getAllPersonal();
        return ResponseEntity.ok(personal);
    }

    /**
     * Obtiene personal por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonalById(@PathVariable Long id) {
        try {
            PersonalPNP personal = personalService.getPersonalById(id);
            return ResponseEntity.ok(personal);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Obtiene personal por CIP
     */
    @GetMapping("/cip/{cip}")
    public ResponseEntity<?> getPersonalByCip(@PathVariable String cip) {
        try {
            PersonalPNP personal = personalService.getPersonalByCip(cip);
            return ResponseEntity.ok(personal);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Obtiene personal por DNI
     */
    @GetMapping("/dni/{dni}")
    public ResponseEntity<?> getPersonalByDni(@PathVariable String dni) {
        try {
            PersonalPNP personal = personalService.getPersonalByDni(dni);
            return ResponseEntity.ok(personal);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Obtiene personal por unidad
     */
    @GetMapping("/unidad/{unidad}")
    public ResponseEntity<List<PersonalPNP>> getPersonalByUnidad(@PathVariable String unidad) {
        List<PersonalPNP> personal = personalService.getPersonalByUnidad(unidad);
        return ResponseEntity.ok(personal);
    }

    /**
     * Busca personal
     */
    @GetMapping("/search")
    public ResponseEntity<List<PersonalPNP>> searchPersonal(@RequestParam String q) {
        List<PersonalPNP> personal = personalService.searchPersonal(q);
        return ResponseEntity.ok(personal);
    }

    /**
     * Crea nuevo personal
     */
    @PostMapping
    public ResponseEntity<?> createPersonal(@Valid @RequestBody PersonalPNP personal) {
        try {
            PersonalPNP nuevoPersonal = personalService.createPersonal(personal);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPersonal);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Actualiza personal existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePersonal(@PathVariable Long id, 
                                           @Valid @RequestBody PersonalPNP personal) {
        try {
            PersonalPNP personalActualizado = personalService.updatePersonal(id, personal);
            return ResponseEntity.ok(personalActualizado);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    /**
     * Elimina personal
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePersonal(@PathVariable Long id) {
        try {
            personalService.deletePersonal(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Personal eliminado exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Obtiene estadísticas
     */
    @GetMapping("/stats")
    public ResponseEntity<PersonalService.PersonalStats> getStats() {
        PersonalService.PersonalStats stats = personalService.getStats();
        return ResponseEntity.ok(stats);
    }

    /**
     * Health check
     */
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "personal-service");
        return ResponseEntity.ok(response);
    }
}
