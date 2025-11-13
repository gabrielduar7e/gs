package com.fiap.skillup.controller;

import com.fiap.skillup.messaging.RecomendacaoProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recomendacoes")
@RequiredArgsConstructor
public class RecomendacaoController {

    private final RecomendacaoProducer producer;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Void> solicitar(@PathVariable Long usuarioId) {
        producer.publicarSolicitacao(usuarioId, "API");
        return ResponseEntity.accepted().build();
    }
}
