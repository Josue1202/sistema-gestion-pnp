package com.pnp.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller temporal para generar hashes BCrypt
 */
@RestController
@RequestMapping("/api/auth/util")
@RequiredArgsConstructor
public class UtilController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/hash/{password}")
    public String generateHash(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
        return hash;
    }
}
