package com.pnp.personal.service;

import com.pnp.personal.model.GradoPolicial;
import com.pnp.personal.repository.GradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradoService {

    private final GradoRepository gradoRepository;

    public List<GradoPolicial> findAll() {
        // Ordenar por jerarquía descendente (Mayor primero)
        return gradoRepository.findAll(Sort.by(Sort.Direction.DESC, "jerarquia"));
    }

    public GradoPolicial findById(Long id) {
        return gradoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado con ID: " + id));
    }

    @Transactional
    public GradoPolicial create(GradoPolicial grado) {
        return gradoRepository.save(grado);
    }

    @Transactional
    public GradoPolicial update(Long id, GradoPolicial grado) {
        GradoPolicial existing = findById(id);
        existing.setNombre(grado.getNombre());
        existing.setJerarquia(grado.getJerarquia());
        existing.setCategoria(grado.getCategoria());
        return gradoRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        GradoPolicial grado = findById(id);
        gradoRepository.delete(grado);
    }
}
