package com.pnp.personal.service;

import com.pnp.personal.model.FuncionPolicial;
import com.pnp.personal.model.PersonalPNP;
import com.pnp.personal.repository.FuncionRepository;
import com.pnp.personal.repository.PersonalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio de gestión de funciones policiales
 */
@Service
@RequiredArgsConstructor
public class FuncionService {

    private final FuncionRepository funcionRepository;
    private final PersonalRepository personalRepository;

    /**
     * Obtiene todas las funciones de un personal
     */
    public List<FuncionPolicial> getFuncionesByPersonalId(Long personalId) {
        return funcionRepository.findByPersonalId(personalId);
    }

    /**
     * Obtiene funciones activas de un personal
     */
    public List<FuncionPolicial> getFuncionesActivasByPersonalId(Long personalId) {
        return funcionRepository.findByPersonalIdAndActivoTrue(personalId);
    }

    /**
     * Obtiene función por ID
     */
    public FuncionPolicial getFuncionById(Long id) {
        return funcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Función no encontrada con ID: " + id));
    }

    /**
     * Crea nueva función
     */
    @Transactional
    public FuncionPolicial createFuncion(Long personalId, FuncionPolicial funcion) {
        PersonalPNP personal = personalRepository.findById(personalId)
                .orElseThrow(() -> new RuntimeException("Personal no encontrado con ID: " + personalId));

        funcion.setPersonal(personal);
        return funcionRepository.save(funcion);
    }

    /**
     * Actualiza función existente
     */
    @Transactional
    public FuncionPolicial updateFuncion(Long id, FuncionPolicial funcionActualizada) {
        FuncionPolicial funcion = getFuncionById(id);

        funcion.setFuncion(funcionActualizada.getFuncion());
        funcion.setFechaAsignacion(funcionActualizada.getFechaAsignacion());
        funcion.setFechaFin(funcionActualizada.getFechaFin());
        funcion.setActivo(funcionActualizada.getActivo());

        return funcionRepository.save(funcion);
    }

    /**
     * Elimina función
     */
    @Transactional
    public void deleteFuncion(Long id) {
        FuncionPolicial funcion = getFuncionById(id);
        funcionRepository.delete(funcion);
    }
}
