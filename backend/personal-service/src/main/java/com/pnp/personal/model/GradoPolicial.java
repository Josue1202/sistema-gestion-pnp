package com.pnp.personal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "grado_policial")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class GradoPolicial {

    @Id
    @Column(name = "id_grado")
    private Long idGrado;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @Column(name = "jerarquia", nullable = false, unique = true)
    private Integer jerarquia;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Categoria categoria;

    public enum Categoria {
        OFICIAL,
        SUBOFICIAL,
        CIVIL
    }
}
