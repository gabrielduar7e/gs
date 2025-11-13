package com.fiap.skillup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
}
