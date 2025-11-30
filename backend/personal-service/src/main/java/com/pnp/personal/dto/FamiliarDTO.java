package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class FamiliarDTO {
    private Long idFamiliar;
    private Long idPersonal;
    private String nombresApellidos;
    private String parentesco; // "CÓNYUGE", "HIJO", etc
    private LocalDate fechaNacimiento;
    private String dni;
    private String lugarNacimiento;
    private Boolean viveConEfectivo;
    private Boolean esDependiente;
}
