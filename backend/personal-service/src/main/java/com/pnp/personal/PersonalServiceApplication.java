package com.pnp.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Servicio de Gestión de Personal
 * Sistema de Gestión de Personal PNP
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PersonalServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalServiceApplication.class, args);
        System.out.println("=================================================");
        System.out.println("   Personal Service - Sistema PNP");
        System.out.println("   Puerto: 8082");
        System.out.println("=================================================");
    }
}
