package com.fiap.skillup.messaging;

import com.fiap.skillup.dto.RecomendacaoDTO;
import com.fiap.skillup.service.AiRecomendacaoService;
import com.fiap.skillup.service.RecomendacaoStoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.fiap.skillup.messaging.RabbitConfig.QUEUE_RECOMENDACOES;

@Slf4j
@Component
@RequiredArgsConstructor
public class RecomendacaoConsumer {

    private final AiRecomendacaoService aiService;
    private final RecomendacaoStoreService storeService;

    @RabbitListener(queues = QUEUE_RECOMENDACOES)
    public void onMensagem(RecomendacaoMensagem mensagem) {
        log.info("[RabbitMQ] Recebida solicitacao de recomendacao: usuarioId={}, origem={}, ts={}",
                mensagem.getUsuarioId(), mensagem.getOrigem(), mensagem.getTimestamp());
        List<RecomendacaoDTO> geradas = aiService.gerarComIaOuMock(mensagem.getUsuarioId());
        storeService.salvar(mensagem.getUsuarioId(), geradas);
        log.info("[RabbitMQ] {} recomendacoes geradas e persistidas para usuario {}", geradas.size(), mensagem.getUsuarioId());
    }
}
