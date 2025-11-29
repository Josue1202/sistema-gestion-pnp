package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FamiliarDTO {
    private Long idFamiliar;
    private String nombresApellidos;
    private LocalDate fechaNac;
    private String lugarNac;
    private String parentesco; // "CONYUGE", "HIJO", etc
}
