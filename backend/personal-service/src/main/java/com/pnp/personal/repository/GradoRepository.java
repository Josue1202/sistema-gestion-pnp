package com.pnp.personal.repository;

import com.pnp.personal.model.GradoPolicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradoRepository extends JpaRepository<GradoPolicial, Long> {

    // Buscar por nombre
    GradoPolicial findByNombre(String nombre);

    // Buscar por categoría
    List<GradoPolicial> findByCategoria(GradoPolicial.Categoria categoria);

    // Ordenar por jerarquía
    List<GradoPolicial> findAllByOrderByJerarquiaAsc();
}
