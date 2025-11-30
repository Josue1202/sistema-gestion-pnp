package com.pnp.personal.repository;

import com.pnp.personal.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    @Query("SELECT c FROM Curso c WHERE c.personal.idPersonal = :idPersonal ORDER BY c.fechaInicio DESC")
    List<Curso> findByPersonalIdPersonalOrderByFechaInicioDesc(Long idPersonal);
}
