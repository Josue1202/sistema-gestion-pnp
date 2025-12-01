package com.pnp.personal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad PersonalPNP - VERSIÓN NORMALIZADA
 * Representa a un miembro del personal de la Policía Nacional del Perú
 * Compatible con BD bd_policia normalizada
 */
@Entity
@Table(name = "personal")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class PersonalPNP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personal")
    private Long idPersonal;

    // Identificación
    @NotBlank(message = "El CIP es obligatorio")
    @Size(max = 20)
    @Column(unique = true, nullable = false, length = 20)
    private String cip;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(unique = true, nullable = false, length = 10)
    private String dni;

    @Column(length = 100)
    private String apellidos;

    @Column(length = 100)
    private String nombres;

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Sexo sexo;

    // Relaciones con tablas maestras (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_grado")
    private GradoPolicial grado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_unidad_actual")
    private UnidadPolicial unidadActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubigeo_nacimiento")
    private Ubigeo ubigeoNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ubigeo_domicilio")
    private Ubigeo ubigeoDomicilio;

    // Información laboral
    @Column(length = 100)
    private String especialidad;

    @Column(length = 20)
    private String codofin;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_incorporacion")
    private LocalDate fechaIncorporacion;

    // Información personal
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil", length = 15)
    private EstadoCivil estadoCivil;

    @Column(name = "n_hijos")
    private Integer nHijos;

    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;

    // Contacto
    @Column(length = 20)
    private String telefono;

    @Column(length = 20)
    private String celular;

    @Column(length = 100)
    private String correo;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    @Column(name = "referencia_domicilio", columnDefinition = "TEXT")
    private String referenciaDomicilio;

    // Datos físicos
    @Column(precision = 4, scale = 2)
    private BigDecimal estatura;

    @Column(precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "talla_camisa", length = 5)
    private String tallaCamisa;

    @Column(name = "talla_calzado", length = 5)
    private String tallaCalzado;

    @Column(name = "color_piel", length = 20)
    private String colorPiel;

    // Educación
    @Column(name = "grado_instruccion", length = 50)
    private String gradoInstruccion;

    @Column(length = 100)
    private String profesion;

    // Multimedia
    @Column(name = "foto_url")
    private String fotoUrl;

    // Estado y auditoría
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Estado estado = Estado.ACTIVO;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Enums
    public enum Sexo {
        M, F
    }

    public enum EstadoCivil {
        SOLTERO, CASADO, DIVORCIADO, VIUDO, CONVIVIENTE
    }

    public enum Estado {
        ACTIVO, BAJA, RETIRO, LICENCIA
    }
}
