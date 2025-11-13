package com.fiap.skillup.mapper;

import com.fiap.skillup.dto.TrilhaAprendizagemDTO;
import com.fiap.skillup.model.TrilhaAprendizagem;

public final class TrilhaAprendizagemMapper {
    private TrilhaAprendizagemMapper() {}

    public static TrilhaAprendizagemDTO toDTO(TrilhaAprendizagem t) {
        if (t == null) return null;
        return TrilhaAprendizagemDTO.builder()
                .id(t.getId())
                .titulo(t.getTitulo())
                .descricao(t.getDescricao())
                .duracaoEstimada(t.getDuracaoEstimada())
                .nivelDificuldade(t.getNivelDificuldade())
                .status(t.getStatus() != null ? t.getStatus().name() : null)
                .usuarioId(t.getUsuario() != null ? t.getUsuario().getId() : null)
                .build();
    }
}
