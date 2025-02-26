package com.example.projetolangcursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.example.projetolangcursos.model")  // Garante que as entidades serão escaneadas
@EnableJpaRepositories(basePackages = "com.example.projetolangcursos.repository")  // Garante que os repositórios serão escaneados
public class ProjetolangcursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetolangcursosApplication.class, args);
    }
}
