package com.pnp.personal.controller;

import com.pnp.personal.dto.CursoDTO;
import com.pnp.personal.model.Curso;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.CursoRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoRepository cursoRepository;
    private final PersonalRepository personalRepository;

    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<CursoDTO>> getByPersonal(@PathVariable Long idPersonal) {
        List<Curso> cursos = cursoRepository.findByPersonalIdPersonalOrderByFechaInicioDesc(idPersonal);
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
        curso.setInstitucion(dto.getInstitucion());
        curso.setFechaInicio(dto.getFechaInicio());
        curso.setFechaFin(dto.getFechaFin());
        curso.setHoras(dto.getHoras());
        curso.setCertificadoUrl(dto.getCertificadoUrl());

        Curso saved = cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!cursoRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CursoDTO toDTO(Curso c) {
        CursoDTO dto = new CursoDTO();
        dto.setIdCurso(c.getIdCurso());
        dto.setIdPersonal(c.getPersonal().getIdPersonal());
        dto.setTipo(c.getTipo().name());
        dto.setNombre(c.getNombre());
        dto.setInstitucion(c.getInstitucion());
        dto.setFechaInicio(c.getFechaInicio());
        dto.setFechaFin(c.getFechaFin());
        dto.setHoras(c.getHoras());
        dto.setCertificadoUrl(c.getCertificadoUrl());
        return dto;
    }
}
