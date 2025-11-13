package com.fiap.skillup.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static com.fiap.skillup.messaging.RabbitConfig.EXCHANGE_RECOMENDACOES;
import static com.fiap.skillup.messaging.RabbitConfig.ROUTING_RECOMENDACOES;

@Service
@RequiredArgsConstructor
public class RecomendacaoProducer {

    private final RabbitTemplate rabbitTemplate;

    public void publicarSolicitacao(Long usuarioId, String origem) {
        RecomendacaoMensagem msg = RecomendacaoMensagem.builder()
                .usuarioId(usuarioId)
                .origem(origem)
                .timestamp(OffsetDateTime.now())
                .build();
        rabbitTemplate.convertAndSend(EXCHANGE_RECOMENDACOES, ROUTING_RECOMENDACOES, msg);
    }
}
