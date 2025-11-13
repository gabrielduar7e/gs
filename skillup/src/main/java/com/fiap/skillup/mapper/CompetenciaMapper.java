package com.fiap.skillup.mapper;

import com.fiap.skillup.dto.CompetenciaDTO;
import com.fiap.skillup.model.Competencia;

public final class CompetenciaMapper {
    private CompetenciaMapper() {}

    public static CompetenciaDTO toDTO(Competencia c) {
        if (c == null) return null;
        return CompetenciaDTO.builder()
                .id(c.getId())
                .nome(c.getNome())
                .descricao(c.getDescricao())
                .categoria(c.getCategoria() != null ? c.getCategoria().name() : null)
                .build();
    }
}
