package com.pnp.personal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unidad_policial")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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
