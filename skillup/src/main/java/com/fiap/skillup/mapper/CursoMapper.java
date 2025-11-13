package com.fiap.skillup.mapper;

import com.fiap.skillup.dto.CursoDTO;
import com.fiap.skillup.model.Curso;

public final class CursoMapper {
    private CursoMapper() {}

    public static CursoDTO toDTO(Curso c) {
        if (c == null) return null;
        return CursoDTO.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .descricao(c.getDescricao())
                .duracaoHoras(c.getDuracaoHoras())
                .nivelDificuldade(c.getNivelDificuldade() != null ? c.getNivelDificuldade().name() : null)
                .preco(c.getPreco())
                .status(c.getStatus() != null ? c.getStatus().name() : null)
                .build();
    }
}
