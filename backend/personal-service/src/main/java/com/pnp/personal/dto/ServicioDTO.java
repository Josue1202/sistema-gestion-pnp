package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ServicioDTO {
    private Long idServicio;
    private String unidad;
    private String cargo;
    private LocalDate desde;
    private LocalDate hasta;
}
