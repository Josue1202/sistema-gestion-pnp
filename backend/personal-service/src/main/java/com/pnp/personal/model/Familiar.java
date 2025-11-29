package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "familiar")
@Data
public class Familiar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_familiar")
    private Long idFamiliar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalPNP personal;

    @Column(name = "nombres_apellidos", length = 200)
    private String nombresApellidos;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;

    @Column(name = "lugar_nac", length = 100)
    private String lugarNac;

    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco", length = 20)
    private Parentesco parentesco;

    public enum Parentesco {
        CÓNYUGE,
        HIJO,
        PADRE,
        MADRE
    }
}
