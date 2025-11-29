package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "ascenso")
@Data
public class Ascenso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ascenso")
    private Long idAscenso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personal", nullable = false)
    private PersonalPNP personal;

    @Column(name = "grado", length = 50)
    private String grado;

    @Column(name = "rd", length = 50)
    private String rd;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "motivo", length = 200)
    private String motivo;
}
