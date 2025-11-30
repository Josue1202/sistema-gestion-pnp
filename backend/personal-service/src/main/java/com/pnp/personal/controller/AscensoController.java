package com.pnp.personal.controller;

import com.pnp.personal.dto.AscensoDTO;
import com.pnp.personal.model.Ascenso;
import com.pnp.personal.model.GradoPolicial;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.AscensoRepository;
import com.pnp.personal.repository.GradoRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ascensos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AscensoController {

    private final AscensoRepository ascensoRepository;
    private final PersonalRepository personalRepository;
    private final GradoRepository gradoRepository;

    // GET: Listar ascensos de un personal
    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<AscensoDTO>> listarAscensosPorPersonal(@PathVariable Long idPersonal) {
        List<Ascenso> ascensos = ascensoRepository.findByPersonalIdPersonalOrderByFechaAscensoDesc(idPersonal);
        List<AscensoDTO> dtos = ascensos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // POST: Crear nuevo ascenso
    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<AscensoDTO> crearAscenso(@PathVariable Long idPersonal, @RequestBody AscensoDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Ascenso ascenso = new Ascenso();
        ascenso.setPersonal(personal);

        // Grado anterior (opcional)
        if (dto.getIdGradoAnterior() != null) {
            GradoPolicial gradoAnterior = gradoRepository.findById(dto.getIdGradoAnterior())
                    .orElseThrow(() -> new RuntimeException("Grado anterior no encontrado"));
            ascenso.setGradoAnterior(gradoAnterior);
        }

        // Grado nuevo (obligatorio)
        GradoPolicial gradoNuevo = gradoRepository.findById(dto.getIdGradoNuevo())
                .orElseThrow(() -> new RuntimeException("Grado nuevo no encontrado"));
        ascenso.setGradoNuevo(gradoNuevo);

        ascenso.setFechaAscenso(dto.getFechaAscenso());
        ascenso.setRdDocumento(dto.getRdDocumento());
        ascenso.setMotivo(dto.getMotivo());

        Ascenso saved = ascensoRepository.save(ascenso);

        // Actualizar grado actual del personal
        personal.setGrado(gradoNuevo);
        personalRepository.save(personal);

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
    }

    // DELETE: Eliminar ascenso
    @DeleteMapping("/{idAscenso}")
    public ResponseEntity<Void> eliminarAscenso(@PathVariable Long idAscenso) {
        if (!ascensoRepository.existsById(idAscenso)) {
            return ResponseEntity.notFound().build();
        }
        ascensoRepository.deleteById(idAscenso);
        return ResponseEntity.noContent().build();
    }

    // Convertir Entity → DTO
    private AscensoDTO convertToDTO(Ascenso ascenso) {
        AscensoDTO dto = new AscensoDTO();
        dto.setIdAscenso(ascenso.getIdAscenso());
        dto.setIdPersonal(ascenso.getPersonal().getIdPersonal());

        if (ascenso.getGradoAnterior() != null) {
            dto.setIdGradoAnterior(ascenso.getGradoAnterior().getIdGrado());
            dto.setNombreGradoAnterior(ascenso.getGradoAnterior().getNombre());
        }

        dto.setIdGradoNuevo(ascenso.getGradoNuevo().getIdGrado());
        dto.setNombreGradoNuevo(ascenso.getGradoNuevo().getNombre());
        dto.setFechaAscenso(ascenso.getFechaAscenso());
        dto.setRdDocumento(ascenso.getRdDocumento());
        dto.setMotivo(ascenso.getMotivo());

        return dto;
    }
}
