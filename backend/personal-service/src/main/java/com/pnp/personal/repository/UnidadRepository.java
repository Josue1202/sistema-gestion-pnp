package com.pnp.personal.repository;

import com.pnp.personal.model.UnidadPolicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnidadRepository extends JpaRepository<UnidadPolicial, Long> {

    // Buscar por nombre
    UnidadPolicial findByNombre(String nombre);

    // Buscar por siglas
    UnidadPolicial findBySiglas(String siglas);

    // Buscar por tipo
    List<UnidadPolicial> findByTipo(UnidadPolicial.TipoUnidad tipo);
}
