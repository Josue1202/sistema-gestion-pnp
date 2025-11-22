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

        // Verificar si el CIP ya existe (si se proporcionó)
        if (request.getCip() != null && usuarioRepository.existsByCip(request.getCip())) {
            throw new RuntimeException("El CIP ya está registrado");
        }

        // Crear nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setCip(request.getCip());
        usuario.setRol(request.getRol());
        usuario.setActivo(true);

        return usuarioRepository.save(usuario);
    }

    /**
     * Autentica un usuario y devuelve un token JWT
     */
    public LoginResponse login(LoginRequest request) {
        // Buscar usuario
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        // Verificar si el usuario está activo
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        // Verificar contraseña
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        // Generar token JWT
        String token = jwtService.generateToken(
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getCip()
        );

        return new LoginResponse(
                token,
                usuario.getId(),
                usuario.getUsername(),
                usuario.getRol(),
                usuario.getCip()
        );
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
