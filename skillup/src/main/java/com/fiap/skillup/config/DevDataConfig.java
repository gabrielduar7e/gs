package com.fiap.skillup.config;

import com.fiap.skillup.model.Usuario;
import com.fiap.skillup.model.Usuario.Perfil;
import com.fiap.skillup.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("dev")
public class DevDataConfig {

    @Bean
    CommandLineRunner seedAdminUser(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@skillup.com";
            if (usuarioRepository.findByEmail(email).isEmpty()) {
                Usuario admin = Usuario.builder()
                        .nome("Admin")
                        .email(email)
                        .senha(passwordEncoder.encode("Senha@123"))
                        .build();
                admin.setActive(true);
                admin.adicionarPerfil(Perfil.ADMIN);
                usuarioRepository.save(admin);
            }
        };
    }
}
