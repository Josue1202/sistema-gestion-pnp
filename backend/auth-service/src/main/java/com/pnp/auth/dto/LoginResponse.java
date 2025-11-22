package com.pnp.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respuesta de login
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String rol;
    private String cip;
    
    public LoginResponse(String token, Long id, String username, String rol, String cip) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.rol = rol;
        this.cip = cip;
    }
}
