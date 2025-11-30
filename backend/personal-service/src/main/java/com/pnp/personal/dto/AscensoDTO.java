package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AscensoDTO {
    private Long idAscenso;
    private Long idPersonal;

    // Grado anterior
    private Long idGradoAnterior;
    private String nombreGradoAnterior;

    // Grado nuevo
    private Long idGradoNuevo;
    private String nombreGradoNuevo;

    private LocalDate fechaAscenso;
    private String rdDocumento;
    private String motivo;
}
