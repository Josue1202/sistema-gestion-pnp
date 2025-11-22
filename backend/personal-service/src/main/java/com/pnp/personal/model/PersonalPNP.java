package com.pnp.personal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad PersonalPNP
 * Representa a un miembro del personal de la Policía Nacional del Perú
 */
@Entity
@Table(name = "personal_pnp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalPNP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El CIP es obligatorio")
    @Size(max = 20, message = "El CIP debe tener máximo 20 caracteres")
    @Column(unique = true, nullable = false, length = 20)
    private String cip;

    @NotBlank(message = "El DNI es obligatorio")
    @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 dígitos")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 100)
    @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 100)
    @Column(name = "apellido_materno", nullable = false, length = 100)
    private String apellidoMaterno;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El género es obligatorio")
    @Pattern(regexp = "[MF]", message = "El género debe ser M o F")
    @Column(nullable = false, length = 1)
    private String genero;

    @Size(max = 15)
    @Column(length = 15)
    private String telefono;

    @Email(message = "El email debe ser válido")
    @Size(max = 100)
    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String direccion;

    @NotBlank(message = "El rango es obligatorio")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String rango;

    @Size(max = 100)
    @Column(length = 100)
    private String especialidad;

    @NotBlank(message = "La unidad es obligatoria")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String unidad;

    @NotBlank(message = "El estado es obligatorio")
    @Column(nullable = false, length = 20)
    private String estado; // ACTIVO, INACTIVO, LICENCIA

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Size(max = 255)
    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionPolicial> funciones = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Método helper para obtener nombre completo
    public String getNombreCompleto() {
        return apellidoPaterno + " " + apellidoMaterno + ", " + nombres;
    }
}
