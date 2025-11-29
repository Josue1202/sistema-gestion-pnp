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
@Table(name = "personal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalPNP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_personal")
    private Long idPersonal;

    @NotBlank(message = "El CIP es obligatorio")
    @Size(max = 20, message = "El CIP debe tener máximo 20 caracteres")
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

    @Column(length = 50)
    private String grado;

    @Column(length = 100)
    private String especialidad;

    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;

    @Column(columnDefinition = "TEXT")
    private String domicilio;

    @Column(length = 50)
    private String distrito;

    @Column(length = 20)
    private String telefono;

    @Column(length = 20)
    private String celular;

    @Column(length = 100)
    private String correo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_egreso")
    private LocalDate fechaEgreso;

    @Column(name = "ultimo_ascenso")
    private LocalDate ultimoAscenso;

    @Column(name = "estado_civil", length = 20)
    private String estadoCivil;

    @Column(name = "n_hijos")
    private Integer nHijos;

    @Column(name = "fecha_incorporacion")
    private LocalDate fechaIncorporacion;

    @Column(length = 50)
    private String division;

    @Column(length = 50)
    private String comisaria;

    @Column(name = "color_piel", length = 20)
    private String colorPiel;

    @Column(precision = 4, scale = 2)
    private Double estatura;

    @Column(precision = 5, scale = 2)
    private Double peso;

    @Column(name = "talla_calzado", length = 5)
    private String tallaCalzado;

    @Column(name = "talla_camisa", length = 5)
    private String tallaCamisa;

    @Column(length = 50)
    private String profesion;

    @OneToMany(mappedBy = "personal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FuncionPolicial> funciones = new ArrayList<>();

    // Método helper para obtener nombre completo
    public String getNombreCompleto() {
        return apellidos + ", " + nombres;
    }
}
