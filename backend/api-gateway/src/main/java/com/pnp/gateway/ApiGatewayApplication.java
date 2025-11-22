package com.pnp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * API Gateway
 * Sistema de Gestión de Personal PNP
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   API Gateway - Sistema PNP");
        System.out.println("   Puerto: 8080");
        System.out.println("   Endpoints disponibles:");
        System.out.println("   - http://localhost:8080/api/auth/**");
        System.out.println("   - http://localhost:8080/api/personal/**");
        System.out.println("   - http://localhost:8080/api/funciones/**");
        System.out.println("=================================================");
    }
}
