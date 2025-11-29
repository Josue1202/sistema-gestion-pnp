package com.pnp.personal.controller;

import com.pnp.personal.dto.FamiliarDTO;
import com.pnp.personal.model.Familiar;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.FamiliarRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/familiares")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class FamiliarController {

    private final FamiliarRepository familiarRepository;
    private final PersonalRepository personalRepository;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<FamiliarDTO>> getByPersonal(@PathVariable Long idPersonal) {
        List<Familiar> familiares = familiarRepository.findByPersonalIdPersonal(idPersonal);
        List<FamiliarDTO> dtos = familiares.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<FamiliarDTO> create(@PathVariable Long idPersonal, @RequestBody FamiliarDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Familiar familiar = new Familiar();
        familiar.setPersonal(personal);
        familiar.setNombresApellidos(dto.getNombresApellidos());
        familiar.setFechaNac(dto.getFechaNac());
        familiar.setLugarNac(dto.getLugarNac());
        familiar.setParentesco(Familiar.Parentesco.valueOf(dto.getParentesco()));

        Familiar saved = familiarRepository.save(familiar);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        familiarRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private FamiliarDTO toDTO(Familiar f) {
        FamiliarDTO dto = new FamiliarDTO();
        dto.setIdFamiliar(f.getIdFamiliar());
        dto.setNombresApellidos(f.getNombresApellidos());
        dto.setFechaNac(f.getFechaNac());
        dto.setLugarNac(f.getLugarNac());
        dto.setParentesco(f.getParentesco().name());
        return dto;
    }
}
