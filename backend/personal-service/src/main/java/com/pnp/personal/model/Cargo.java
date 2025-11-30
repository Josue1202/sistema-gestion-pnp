package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cargo")
@Data
public class Cargo {

    @Id
    @Column(name = "id_cargo")
    private Long idCargo;

    @Column(name = "nombre_cargo", nullable = false)
    private String nombreCargo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "nivel_jerarquico")
    private Integer nivelJerarquico;
}
