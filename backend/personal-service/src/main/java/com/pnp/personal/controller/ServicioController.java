package com.pnp.personal.controller;

import com.pnp.personal.dto.ServicioPrestadoDTO;
import com.pnp.personal.model.ServicioPrestado;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.model.UnidadPolicial;
import com.pnp.personal.repository.ServicioRepository;
import com.pnp.personal.repository.PersonalRepository;
import com.pnp.personal.repository.UnidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioRepository servicioRepository;
    private final PersonalRepository personalRepository;
    private final UnidadRepository unidadRepository;

    // GET: Listar servicios de un personal
    @GetMapping("/personal/{idPersonal}")
    public ResponseEntity<List<ServicioPrestadoDTO>> listarServiciosPorPersonal(@PathVariable Long idPersonal) {
        List<ServicioPrestado> servicios = servicioRepository
                .findByPersonalIdPersonalOrderByFechaInicioDesc(idPersonal);
        List<ServicioPrestadoDTO> dtos = servicios.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // POST: Crear nuevo servicio
    @PostMapping("/personal/{idPersonal}")
    public ResponseEntity<ServicioPrestadoDTO> crearServicio(@PathVariable Long idPersonal,
            @RequestBody ServicioPrestadoDTO dto) {
        PersonalPNP personal = personalRepository.findById(idPersonal)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado"));

        UnidadPolicial unidad = unidadRepository.findById(dto.getIdUnidad())
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada"));

        ServicioPrestado servicio = new ServicioPrestado();
        servicio.setPersonal(personal);
        servicio.setUnidad(unidad);
        servicio.setCargo(dto.getCargo());
        servicio.setFechaInicio(dto.getFechaInicio());
        servicio.setFechaFin(dto.getFechaFin());
        servicio.setMotivoSalida(dto.getMotivoSalida());

        ServicioPrestado saved = servicioRepository.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(saved));
    }

    // DELETE: Eliminar servicio
    @DeleteMapping("/{idServicio}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Long idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            return ResponseEntity.notFound().build();
        }
        servicioRepository.deleteById(idServicio);
        return ResponseEntity.noContent().build();
    }

    // Convertir Entity → DTO
    private ServicioPrestadoDTO convertToDTO(ServicioPrestado servicio) {
        ServicioPrestadoDTO dto = new ServicioPrestadoDTO();
        dto.setIdServicio(servicio.getIdServicio());
        dto.setIdPersonal(servicio.getPersonal().getIdPersonal());
        dto.setIdUnidad(servicio.getUnidad().getIdUnidad());
        dto.setNombreUnidad(servicio.getUnidad().getNombre());
        dto.setSiglasUnidad(servicio.getUnidad().getSiglas());
        dto.setCargo(servicio.getCargo());
        dto.setFechaInicio(servicio.getFechaInicio());
        dto.setFechaFin(servicio.getFechaFin());
        dto.setMotivoSalida(servicio.getMotivoSalida());
        return dto;
    }
}
