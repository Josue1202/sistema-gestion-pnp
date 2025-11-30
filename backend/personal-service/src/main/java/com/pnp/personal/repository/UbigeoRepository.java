package com.pnp.personal.repository;

import com.pnp.personal.model.Ubigeo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbigeoRepository extends JpaRepository<Ubigeo, Long> {

    // Buscar por departamento
    List<Ubigeo> findByDepartamento(String departamento);

    // Buscar por departamento y provincia
    List<Ubigeo> findByDepartamentoAndProvincia(String departamento, String provincia);

    // Buscar ubicación completa
    Ubigeo findByDepartamentoAndProvinciaAndDistrito(String departamento, String provincia, String distrito);
}
