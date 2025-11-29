package com.pnp.personal.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CursoDTO {
    private Long idCurso;
    private String tipo; // "INSTITUCIONAL" o "EXTRA"
    private String nombre;
    private String lugar;
    private LocalDate fecha;
}
