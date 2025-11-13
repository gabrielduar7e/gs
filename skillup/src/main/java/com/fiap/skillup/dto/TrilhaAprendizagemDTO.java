package com.fiap.skillup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrilhaAprendizagemDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Integer duracaoEstimada;
    private Integer nivelDificuldade;
    private String status;
    private Long usuarioId;
}
