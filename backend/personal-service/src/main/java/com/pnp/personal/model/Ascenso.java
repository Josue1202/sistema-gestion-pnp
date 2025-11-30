package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "ascenso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ascenso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ascenso")
    private Long idAscenso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalPNP personal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado_anterior")
    private GradoPolicial gradoAnterior;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado_nuevo", nullable = false)
    private GradoPolicial gradoNuevo;

    @Column(name = "fecha_ascenso", nullable = false)
    private LocalDate fechaAscenso;

    @Column(name = "rd_documento", length = 50)
    private String rdDocumento;

    @Column(columnDefinition = "TEXT")
    private String motivo;
}
