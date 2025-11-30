package com.pnp.personal.controller;

import com.pnp.personal.dto.FamiliarDTO;
import com.pnp.personal.model.Familiar;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.FamiliarRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/familiares")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
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
        familiar.setParentesco(Familiar.Parentesco.valueOf(dto.getParentesco()));
        familiar.setFechaNacimiento(dto.getFechaNacimiento());
        familiar.setDni(dto.getDni());
        familiar.setLugarNacimiento(dto.getLugarNacimiento());
        familiar.setViveConEfectivo(dto.getViveConEfectivo());
        familiar.setEsDependiente(dto.getEsDependiente());

        Familiar saved = familiarRepository.save(familiar);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!familiarRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        familiarRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private FamiliarDTO toDTO(Familiar f) {
        FamiliarDTO dto = new FamiliarDTO();
        dto.setIdFamiliar(f.getIdFamiliar());
        dto.setIdPersonal(f.getPersonal().getIdPersonal());
        dto.setNombresApellidos(f.getNombresApellidos());
        dto.setParentesco(f.getParentesco().name());
        dto.setFechaNacimiento(f.getFechaNacimiento());
        dto.setDni(f.getDni());
        dto.setLugarNacimiento(f.getLugarNacimiento());
        dto.setViveConEfectivo(f.getViveConEfectivo());
        dto.setEsDependiente(f.getEsDependiente());
        return dto;
    }
}
