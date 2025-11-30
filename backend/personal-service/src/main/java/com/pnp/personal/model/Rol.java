package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "rol")
@Data
public class Rol {

    @Id
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre_rol", unique = true, nullable = false)
    private String nombreRol;

    @Column(name = "descripcion")
    private String descripcion;
}
