package com.pnp.personal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad FuncionPolicial
 * Representa las funciones asignadas a un miembro del personal PNP
 */
@Entity
@Table(name = "funciones_policial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FuncionPolicial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personal_id", nullable = false)
    @JsonIgnore
    private PersonalPNP personal;

    @NotBlank(message = "La descripción de la función es obligatoria")
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String funcion;

    @NotNull(message = "La fecha de asignación es obligatoria")
    @Column(name = "fecha_asignacion", nullable = false)
    private LocalDate fechaAsignacion;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Boolean activo = true;
}
