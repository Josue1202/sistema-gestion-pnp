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

    Boolean existsByCip(String cip);

    Boolean existsByDni(String dni);

    @Query("SELECT p FROM PersonalPNP p WHERE " +
            "LOWER(p.nombres) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.apellidos) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.cip) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(p.dni) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<PersonalPNP> searchPersonal(@Param("query") String query);

    @Query("SELECT g.nombre as nombre, COUNT(p) as cantidad FROM PersonalPNP p " +
            "JOIN p.grado g GROUP BY g.nombre ORDER BY cantidad DESC")
    List<Object[]> countByGrado();

    @Query("SELECT u.nombre as nombre, COUNT(p) as cantidad FROM PersonalPNP p " +
            "JOIN p.unidadActual u GROUP BY u.nombre ORDER BY cantidad DESC")
    List<Object[]> countByUnidad();
}
