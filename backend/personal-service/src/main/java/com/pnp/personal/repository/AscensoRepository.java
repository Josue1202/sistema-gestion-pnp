package com.pnp.personal.repository;

import com.pnp.personal.model.Ascenso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AscensoRepository extends JpaRepository<Ascenso, Long> {
    List<Ascenso> findByPersonalIdPersonalOrderByFechaDesc(Long idPersonal);
}
