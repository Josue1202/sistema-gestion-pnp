package com.pnp.personal.controller;

import com.pnp.personal.dto.AscensoDTO;
import com.pnp.personal.model.Ascenso;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.AscensoRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ascensos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AscensoController {

    private final AscensoRepository ascensoRepository;
    private final PersonalRepository personalRepository;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<AscensoDTO>> getByPersonal(@PathVariable Long idPersonal) {
        List<Ascenso> ascensos = ascensoRepository.findByPersonalIdPersonalOrderByFechaDesc(idPersonal);
        List<AscensoDTO> dtos = ascensos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<AscensoDTO> create(@PathVariable Long idPersonal, @RequestBody AscensoDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Ascenso ascenso = new Ascenso();
        ascenso.setPersonal(personal);
        ascenso.setGrado(dto.getGrado());
        ascenso.setRd(dto.getRd());
        ascenso.setFecha(dto.getFecha());
        ascenso.setMotivo(dto.getMotivo());

        Ascenso saved = ascensoRepository.save(ascenso);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ascensoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private AscensoDTO toDTO(Ascenso a) {
        AscensoDTO dto = new AscensoDTO();
        dto.setIdAscenso(a.getIdAscenso());
        dto.setGrado(a.getGrado());
        dto.setRd(a.getRd());
        dto.setFecha(a.getFecha());
        dto.setMotivo(a.getMotivo());
        return dto;
    }
}
