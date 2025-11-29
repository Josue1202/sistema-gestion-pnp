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

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "lugar", length = 100)
    private String lugar;

    @Column(name = "fecha")
    private LocalDate fecha;

    public enum TipoCurso {
        INSTITUCIONAL,
        EXTRA
    }
}
