package com.pnp.personal.repository;

import com.pnp.personal.model.FuncionPolicial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad FuncionPolicial
 */
@Repository
public interface FuncionRepository extends JpaRepository<FuncionPolicial, Long> {

    List<FuncionPolicial> findByPersonalIdPersonal(Long idPersonal);

    List<FuncionPolicial> findByPersonalIdPersonalAndActivoTrue(Long idPersonal);
}
