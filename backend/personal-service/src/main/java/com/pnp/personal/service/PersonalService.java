package com.pnp.personal.service;

import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de gestión de personal PNP
 */
@Service
@RequiredArgsConstructor
public class PersonalService {

    private final PersonalRepository personalRepository;

    /**
     * Obtiene todo el personal
     */
    public List<PersonalPNP> getAllPersonal() {
        return personalRepository.findAll();
    }

    /**
     * Obtiene personal por ID
     */
    public PersonalPNP getPersonalById(Long id) {
        return personalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con ID: " + id));
    }

    /**
     * Obtiene personal por CIP
     */
    public PersonalPNP getPersonalByCip(String cip) {
        return personalRepository.findByCip(cip)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con CIP: " + cip));
    }

    /**
     * Obtiene personal por DNI
     */
    public PersonalPNP getPersonalByDni(String dni) {
        return personalRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con DNI: " + dni));
    }

    /**
     * Obtiene personal por unidad
     */
    public List<PersonalPNP> getPersonalByUnidad(String unidad) {
        return personalRepository.findByUnidad(unidad);
    }

    /**
     * Busca personal por query
     */
    public List<PersonalPNP> searchPersonal(String query) {
        return personalRepository.searchPersonal(query);
    }

    /**
     * Crea nuevo personal
     */
    @Transactional
    public PersonalPNP createPersonal(PersonalPNP personal) {
        // Validar que el CIP no exista
        if (personalRepository.existsByCip(personal.getCip())) {
            throw new RuntimeException("El CIP ya está registrado: " + personal.getCip());
        }

        // Validar que el DNI no exista
        if (personalRepository.existsByDni(personal.getDni())) {
            throw new RuntimeException("El DNI ya está registrado: " + personal.getDni());
        }

        return personalRepository.save(personal);
    }

    /**
     * Actualiza personal existente
     */
    @Transactional
    public PersonalPNP updatePersonal(Long id, PersonalPNP personalActualizado) {
        PersonalPNP personal = getPersonalById(id);

        // Validar que el CIP no esté siendo usado por otro personal
        if (!personal.getCip().equals(personalActualizado.getCip()) &&
            personalRepository.existsByCip(personalActualizado.getCip())) {
            throw new RuntimeException("El CIP ya está registrado: " + personalActualizado.getCip());
        }

        // Validar que el DNI no esté siendo usado por otro personal
        if (!personal.getDni().equals(personalActualizado.getDni()) &&
            personalRepository.existsByDni(personalActualizado.getDni())) {
            throw new RuntimeException("El DNI ya está registrado: " + personalActualizado.getDni());
        }

        // Actualizar campos
        personal.setCip(personalActualizado.getCip());
        personal.setDni(personalActualizado.getDni());
        personal.setApellidoPaterno(personalActualizado.getApellidoPaterno());
        personal.setApellidoMaterno(personalActualizado.getApellidoMaterno());
        personal.setNombres(personalActualizado.getNombres());
        personal.setFechaNacimiento(personalActualizado.getFechaNacimiento());
        personal.setGenero(personalActualizado.getGenero());
        personal.setTelefono(personalActualizado.getTelefono());
        personal.setEmail(personalActualizado.getEmail());
        personal.setDireccion(personalActualizado.getDireccion());
        personal.setRango(personalActualizado.getRango());
        personal.setEspecialidad(personalActualizado.getEspecialidad());
        personal.setUnidad(personalActualizado.getUnidad());
        personal.setEstado(personalActualizado.getEstado());
        personal.setFechaIngreso(personalActualizado.getFechaIngreso());
        personal.setFotoUrl(personalActualizado.getFotoUrl());

        return personalRepository.save(personal);
    }

    /**
     * Elimina personal
     */
    @Transactional
    public void deletePersonal(Long id) {
        PersonalPNP personal = getPersonalById(id);
        personalRepository.delete(personal);
    }

    /**
     * Obtiene estadísticas generales
     */
    public PersonalStats getStats() {
        List<PersonalPNP> allPersonal = personalRepository.findAll();
        long activos = allPersonal.stream().filter(p -> "ACTIVO".equals(p.getEstado())).count();
        long inactivos = allPersonal.stream().filter(p -> "INACTIVO".equals(p.getEstado())).count();
        long enLicencia = allPersonal.stream().filter(p -> "LICENCIA".equals(p.getEstado())).count();

        PersonalStats stats = new PersonalStats();
        stats.setTotal(allPersonal.size());
        stats.setActivos(activos);
        stats.setInactivos(inactivos);
        stats.setEnLicencia(enLicencia);

        return stats;
    }

    // Clase interna para estadísticas
    @lombok.Data
    public static class PersonalStats {
        private long total;
        private long activos;
        private long inactivos;
        private long enLicencia;
    }
}
