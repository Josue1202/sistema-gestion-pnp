package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "curso")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalPNP personal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20)
    private TipoCurso tipo;

    @Column(name = "nombre", length = 150)
    private String nombre;

    @Column(name = "institucion", length = 100)
    private String institucion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "horas")
    private Integer horas;

    @Column(name = "certificado_url")
    private String certificadoUrl;

    public enum TipoCurso {
        INSTITUCIONAL,
        EXTRA_INSTITUCIONAL
    }
}
