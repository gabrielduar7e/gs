package com.fiap.skillup.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.fiap.skillup.messaging.RabbitConfig.QUEUE_RECOMENDACOES;

@Slf4j
@Component
public class RecomendacaoConsumer {

    @RabbitListener(queues = QUEUE_RECOMENDACOES)
    public void onMensagem(RecomendacaoMensagem mensagem) {
        log.info("[RabbitMQ] Recebida solicitacao de recomendacao: usuarioId={}, origem={}, ts={}",
                mensagem.getUsuarioId(), mensagem.getOrigem(), mensagem.getTimestamp());
        // TODO: integrar com Spring AI para gerar recomendacoes e persistir/retornar
    }
}
