package com.fiap.skillup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Integer duracaoHoras;
    private Integer nivelDificuldade;
    private BigDecimal preco;
    private String status;
}
