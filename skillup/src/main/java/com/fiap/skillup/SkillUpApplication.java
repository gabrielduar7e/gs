package com.fiap.skillup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Classe principal da aplicação SkillUp
 * Plataforma de requalificação profissional com IA
 */
@SpringBootApplication
@EnableCaching
public class SkillUpApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkillUpApplication.class, args);
    }
}
