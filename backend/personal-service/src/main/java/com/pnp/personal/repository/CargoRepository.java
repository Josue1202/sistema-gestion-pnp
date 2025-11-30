package com.pnp.personal.repository;

import com.pnp.personal.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    // Buscar por nombre
    Cargo findByNombreCargo(String nombreCargo);

    // Ordenar por nivel jerárquico
    List<Cargo> findAllByOrderByNivelJerarquicoAsc();
}
