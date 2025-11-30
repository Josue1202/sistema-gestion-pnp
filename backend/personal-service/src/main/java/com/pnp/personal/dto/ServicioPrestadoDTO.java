package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ServicioPrestadoDTO {
    private Long idServicio;
    private Long idPersonal;

    // Unidad
    private Long idUnidad;
    private String nombreUnidad;
    private String siglasUnidad;

    private String cargo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivoSalida;
}
