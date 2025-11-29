package com.pnp.personal.controller;

import com.pnp.personal.dto.ServicioDTO;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.model.Servicio;
import com.pnp.personal.repository.PersonalRepository;
import com.pnp.personal.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ServicioController {

    private final ServicioRepository servicioRepository;
    private final PersonalRepository personalRepository;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<ServicioDTO>> getByPersonal(@PathVariable Long idPersonal) {
        List<Servicio> servicios = servicioRepository.findByPersonalIdPersonalOrderByDesdeDesc(idPersonal);
        List<ServicioDTO> dtos = servicios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<ServicioDTO> create(@PathVariable Long idPersonal, @RequestBody ServicioDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Servicio servicio = new Servicio();
        servicio.setPersonal(personal);
        servicio.setUnidad(dto.getUnidad());
        servicio.setCargo(dto.getCargo());
        servicio.setDesde(dto.getDesde());
        servicio.setHasta(dto.getHasta());

        Servicio saved = servicioRepository.save(servicio);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        servicioRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ServicioDTO toDTO(Servicio s) {
        ServicioDTO dto = new ServicioDTO();
        dto.setIdServicio(s.getIdServicio());
        dto.setUnidad(s.getUnidad());
        dto.setCargo(s.getCargo());
        dto.setDesde(s.getDesde());
        dto.setHasta(s.getHasta());
        return dto;
    }
}
