package com.pnp.auth.service;

import com.pnp.auth.dto.LoginRequest;
import com.pnp.auth.dto.LoginResponse;
import com.pnp.auth.dto.RegisterRequest;
import com.pnp.auth.model.Usuario;
import com.pnp.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio de autenticación
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Registra un nuevo usuario
     */
    @Transactional
    public Usuario register(RegisterRequest request) {
        // Verificar si el username ya existe
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setIdRol(1L); // ROL por defecto (OPERADOR)
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica un usuario y devuelve un token JWT
     */
    public LoginResponse login(LoginRequest request) {
        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Username: " + request.getUsername());

        // Buscar usuario
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> {
                    System.out.println("ERROR: Usuario no encontrado");
                    return new RuntimeException("Usuario o contraseña incorrectos");
                });

        System.out.println("Usuario encontrado: " + usuario.getUsername());
        System.out.println("Password hash: " + usuario.getPassword());
        System.out.println("Activo: " + usuario.getActivo());

        // Verificar si el usuario está activo
        if (usuario.getActivo() == null || !usuario.getActivo()) {
            System.out.println("ERROR: Usuario inactivo");
            throw new RuntimeException("Usuario inactivo");
        }

        System.out.println("Verificando contraseña...");
        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            System.out.println("ERROR: Contraseña incorrecta");
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        System.out.println("Contraseña correcta, generando token...");
        // Generar token JWT
        String token = jwtService.generateToken(
                usuario.getUsername(),
                "USER",
                null);

        System.out.println("Token generado exitosamente");
        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getUsername(),
                "USER",
                null);
    }

    /**
     * Valida un token JWT
     */
    public Boolean validateToken(String token, String username) {
        return jwtService.validateToken(token, username);
    }

    /**
     * Obtiene información del usuario por username
     */
    public Usuario getUserByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}
