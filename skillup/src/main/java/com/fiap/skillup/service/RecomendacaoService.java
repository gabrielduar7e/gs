package com.fiap.skillup.service;

import com.fiap.skillup.dto.RecomendacaoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecomendacaoService {

    public List<RecomendacaoDTO> gerarMock(Long usuarioId) {
        // Mock simples de recomendações para demo; integração com IA virá depois
        return List.of(
                RecomendacaoDTO.builder()
                        .titulo("Trilha Java Intermediária")
                        .descricao("Aprofunde seus conhecimentos em coleções, streams e boas práticas")
                        .score(0.92)
                        .build(),
                RecomendacaoDTO.builder()
                        .titulo("Curso de Spring Boot com JPA")
                        .descricao("Crie APIs REST com persistência e validação")
                        .score(0.87)
                        .build(),
                RecomendacaoDTO.builder()
                        .titulo("Introdução a Cloud na Azure")
                        .descricao("Conceitos de deploy e serviços gerenciados")
                        .score(0.81)
                        .build()
        );
    }
}
