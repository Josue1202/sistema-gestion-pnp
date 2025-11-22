package com.pnp.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Servicio de Autenticación
 * Sistema de Gestión de Personal PNP
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   Auth Service - Sistema PNP");
        System.out.println("   Puerto: 8081");
        System.out.println("=================================================");
    }
}
