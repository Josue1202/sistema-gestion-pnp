package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CursoDTO {
    private Long idCurso;
    private Long idPersonal;
    private String tipo; // "INSTITUCIONAL" o "EXTRA_INSTITUCIONAL"
    private String nombre;
    private String institucion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Integer horas;
    private String certificadoUrl;
}
