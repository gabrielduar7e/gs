package com.fiap.skillup.controller;

import com.fiap.skillup.dto.RecomendacaoDTO;
import com.fiap.skillup.messaging.RecomendacaoProducer;
import com.fiap.skillup.service.RecomendacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recomendacoes")
@RequiredArgsConstructor
public class RecomendacaoController {

    private final RecomendacaoProducer producer;
    private final RecomendacaoService recomendacaoService;

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Void> solicitar(@PathVariable Long usuarioId) {
        producer.publicarSolicitacao(usuarioId, "API");
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{usuarioId}/gerar")
    public ResponseEntity<List<RecomendacaoDTO>> gerar(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(recomendacaoService.gerarMock(usuarioId));
    }
}
