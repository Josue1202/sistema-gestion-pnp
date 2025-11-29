package com.pnp.personal.repository;

import com.pnp.personal.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByPersonalIdPersonalOrderByDesdeDesc(Long idPersonal);
}
