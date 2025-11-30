package com.pnp.auth.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad Usuario
 * Representa un usuario del sistema con credenciales de acceso
 */
@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "id_personal")
    private Long idPersonal;

    @Column(name = "id_rol")
    private Long idRol;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 50, message = "El nombre de usuario debe tener entre 4 y 50 caracteres")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "estado", nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean activo = true;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    // Campos transitorios para compatibilidad
    @Transient
    private String cip;

    @Transient
    private String rol;

    @PrePersist
    protected void onCreate() {
        if (activo == null) {
            activo = true;
        }
    }
}
