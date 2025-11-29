package com.pnp.personal.repository;

import com.pnp.personal.model.Familiar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FamiliarRepository extends JpaRepository<Familiar, Long> {
    List<Familiar> findByPersonalIdPersonal(Long idPersonal);
}
