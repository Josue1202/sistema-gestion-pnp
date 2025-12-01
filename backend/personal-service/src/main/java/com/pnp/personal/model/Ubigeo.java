package com.pnp.personal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ubigeo")
@Data
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
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
