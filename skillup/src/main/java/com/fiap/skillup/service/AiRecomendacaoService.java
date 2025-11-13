package com.fiap.skillup.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.skillup.dto.RecomendacaoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiRecomendacaoService {

    private final RecomendacaoService mockService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${aplicacao.ia.chave-api:}")
    private String apiKey;

    @Value("${aplicacao.ia.endpoint:https://api.openai.com/v1/chat/completions}")
    private String endpoint;

    @Value("${aplicacao.ia.modelo:gpt-4o-mini}")
    private String modelo;

    public List<RecomendacaoDTO> gerarComIaOuMock(Long usuarioId) {
        if (apiKey == null || apiKey.isBlank()) {
            log.warn("Chave de IA não configurada. Usando mock de recomendações.");
            return mockService.gerarMock(usuarioId);
        }
        try {
            RestTemplate rest = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String prompt = "Sugira 3 recomendações de cursos/trilhas com título, descrição e score (0-1) em JSON para o usuário " + usuarioId + ". Formato: {\"recomendacoes\":[{\"titulo\":...,\"descricao\":...,\"score\":...},...] }";
            Map<String, Object> body = Map.of(
                    "model", modelo,
                    "messages", List.of(
                            Map.of("role", "system", "content", "Você é um assistente que gera recomendações educacionais."),
                            Map.of("role", "user", "content", prompt)
                    )
            );
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> resp = rest.postForEntity(endpoint, entity, String.class);
            if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
                log.warn("Resposta não OK da IA: status={}", resp.getStatusCode());
                return mockService.gerarMock(usuarioId);
            }
            JsonNode root = objectMapper.readTree(resp.getBody());
            JsonNode contentNode = root.path("choices").path(0).path("message").path("content");
            if (contentNode.isMissingNode() || contentNode.asText().isBlank()) {
                return mockService.gerarMock(usuarioId);
            }
            // O content pode vir como um JSON string; tentar parsear
            String content = contentNode.asText();
            List<RecomendacaoDTO> lista = new ArrayList<>();
            try {
                JsonNode parsed = objectMapper.readTree(content);
                JsonNode recs = parsed.path("recomendacoes");
                if (recs.isArray()) {
                    for (JsonNode r : recs) {
                        lista.add(RecomendacaoDTO.builder()
                                .titulo(r.path("titulo").asText("Recomendação"))
                                .descricao(r.path("descricao").asText("Descrição"))
                                .score(r.path("score").asDouble(0.8))
                                .build());
                    }
                }
            } catch (Exception e) {
                log.warn("Falha ao parsear conteúdo da IA, retornando mock. Erro: {}", e.toString());
                return mockService.gerarMock(usuarioId);
            }
            if (lista.isEmpty()) {
                return mockService.gerarMock(usuarioId);
            }
            return lista;
        } catch (Exception ex) {
            log.warn("Erro chamando IA, usando mock. Erro: {}", ex.toString());
            return mockService.gerarMock(usuarioId);
        }
    }
}
