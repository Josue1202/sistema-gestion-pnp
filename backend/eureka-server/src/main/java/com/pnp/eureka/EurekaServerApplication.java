package com.pnp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Servidor Eureka - Descubrimiento de Servicios
 * Sistema de Gestión de Personal PNP
 * 
 * Este servidor permite el registro automático y descubrimiento
 * de todos los microservicios del sistema.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   Eureka Server - Sistema PNP");
        System.out.println("   Dashboard: http://localhost:8761");
        System.out.println("=================================================");
    }
}
