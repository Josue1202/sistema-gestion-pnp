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

    @Column(name = "nombres_apellidos", length = 100)
    private String nombresApellidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco", length = 20)
    private Parentesco parentesco;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "dni", length = 10)
    private String dni;

    @Column(name = "lugar_nacimiento", length = 100)
    private String lugarNacimiento;

    @Column(name = "vive_con_efectivo")
    private Boolean viveConEfectivo;

    @Column(name = "es_dependiente")
    private Boolean esDependiente;

    public enum Parentesco {
        CÓNYUGE,
        HIJO,
        HIJA,
        PADRE,
        MADRE,
        HERMANO,
        HERMANA
    }
}
