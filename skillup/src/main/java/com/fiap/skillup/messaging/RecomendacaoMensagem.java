package com.fiap.skillup.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecomendacaoMensagem {
    private Long usuarioId;
    private String origem; // e.g., "API"
    private OffsetDateTime timestamp;
}
