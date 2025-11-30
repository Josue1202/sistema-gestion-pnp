package com.pnp.personal.repository;

import com.pnp.personal.model.Ascenso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AscensoRepository extends JpaRepository<Ascenso, Long> {

    @Query("SELECT a FROM Ascenso a WHERE a.personal.idPersonal = :idPersonal ORDER BY a.fechaAscenso DESC")
    List<Ascenso> findByPersonalIdPersonalOrderByFechaAscensoDesc(Long idPersonal);
}
