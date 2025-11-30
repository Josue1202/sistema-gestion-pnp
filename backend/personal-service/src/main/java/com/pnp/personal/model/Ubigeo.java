package com.pnp.personal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ubigeo")
@Data
public class Ubigeo {

    @Id
    @Column(name = "id_ubigeo")
    private Long idUbigeo;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "distrito")
    private String distrito;
}
