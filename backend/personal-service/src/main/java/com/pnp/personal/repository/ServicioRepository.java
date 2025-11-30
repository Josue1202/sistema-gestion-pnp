package com.pnp.personal.repository;

import com.pnp.personal.model.ServicioPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioPrestado, Long> {

    @Query("SELECT s FROM ServicioPrestado s WHERE s.personal.idPersonal = :idPersonal ORDER BY s.fechaInicio DESC")
    List<ServicioPrestado> findByPersonalIdPersonalOrderByFechaInicioDesc(Long idPersonal);

    // Servicios actuales (sin fecha fin)
    @Query("SELECT s FROM ServicioPrestado s WHERE s.personal.idPersonal = :idPersonal AND s.fechaFin IS NULL")
    List<ServicioPrestado> findServiciosActualesByPersonal(Long idPersonal);
}
