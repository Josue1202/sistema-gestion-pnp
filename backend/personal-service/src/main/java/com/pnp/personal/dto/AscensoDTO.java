package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AscensoDTO {
    private Long idAscenso;
    private String grado;
    private String rd;
    private LocalDate fecha;
    private String motivo;
}
