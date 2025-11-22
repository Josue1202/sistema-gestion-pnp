package com.pnp.personal.repository;

import com.pnp.personal.model.PersonalPNP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad PersonalPNP
 */
@Repository
public interface PersonalRepository extends JpaRepository<PersonalPNP, Long> {
    
    Optional<PersonalPNP> findByCip(String cip);
    
    Optional<PersonalPNP> findByDni(String dni);
    
    List<PersonalPNP> findByUnidad(String unidad);
    
    List<PersonalPNP> findByEstado(String estado);
    
    List<PersonalPNP> findByRango(String rango);
    
    Boolean existsByCip(String cip);
    
    Boolean existsByDni(String dni);
    
    @Query("SELECT p FROM PersonalPNP p WHERE " +
           "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.apellidoPaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.apellidoMaterno) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.cip) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(p.dni) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<PersonalPNP> searchPersonal(@Param("query") String query);
}
