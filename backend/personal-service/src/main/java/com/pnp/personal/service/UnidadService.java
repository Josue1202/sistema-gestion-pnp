package com.pnp.personal.service;

import com.pnp.personal.model.UnidadPolicial;
import com.pnp.personal.repository.UnidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadService {

    private final UnidadRepository unidadRepository;

    public List<UnidadPolicial> findAll() {
        return unidadRepository.findAll();
    }

    public UnidadPolicial findById(Long id) {
        return unidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidad no encontrada con ID: " + id));
    }

    @Transactional
    public UnidadPolicial create(UnidadPolicial unidad) {
        return unidadRepository.save(unidad);
    }

    @Transactional
    public UnidadPolicial update(Long id, UnidadPolicial unidad) {
        UnidadPolicial existing = findById(id);
        existing.setNombre(unidad.getNombre());
        existing.setSiglas(unidad.getSiglas());
        existing.setTipo(unidad.getTipo());
        return unidadRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        UnidadPolicial unidad = findById(id);
        unidadRepository.delete(unidad);
    }
}
