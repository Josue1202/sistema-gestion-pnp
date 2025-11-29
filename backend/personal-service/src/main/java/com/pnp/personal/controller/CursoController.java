package com.pnp.personal.controller;

import com.pnp.personal.dto.CursoDTO;
import com.pnp.personal.model.Curso;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.CursoRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CursoController {

    private final CursoRepository cursoRepository;
    private final PersonalRepository personalRepository;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<CursoDTO>> getByPersonal(@PathVariable Long idPersonal) {
        List<Curso> cursos = cursoRepository.findByPersonalIdPersonalOrderByFechaDesc(idPersonal);
        List<CursoDTO> dtos = cursos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<CursoDTO> create(@PathVariable Long idPersonal, @RequestBody CursoDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        Curso curso = new Curso();
        curso.setPersonal(personal);
        curso.setTipo(Curso.TipoCurso.valueOf(dto.getTipo()));
        curso.setNombre(dto.getNombre());
        curso.setLugar(dto.getLugar());
        curso.setFecha(dto.getFecha());

        Curso saved = cursoRepository.save(curso);
        return ResponseEntity.ok(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cursoRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private CursoDTO toDTO(Curso c) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(c.getIdCurso());
        dto.setTipo(c.getTipo().name());
        dto.setNombre(c.getNombre());
        dto.setLugar(c.getLugar());
        dto.setFecha(c.getFecha());
        return dto;
    }
}
