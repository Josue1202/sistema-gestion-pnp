package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidad_policial")
@Data
public class UnidadPolicial {

    @Id
    @Column(name = "id_unidad")
    private Long idUnidad;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "siglas")
    private String siglas;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoUnidad tipo;

    public enum TipoUnidad {
        COMISARIA,
        DIVISION,
        REGION,
        ADMINISTRATIVO,
        ESPECIAL
    }
}
