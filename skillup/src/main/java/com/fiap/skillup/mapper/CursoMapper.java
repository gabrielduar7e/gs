package com.fiap.skillup.mapper;

import com.fiap.skillup.dto.CursoDTO;
import com.fiap.skillup.model.Curso;

import java.time.Duration;

public final class CursoMapper {
    private CursoMapper() {}

    public static CursoDTO toDTO(Curso c) {
        if (c == null) return null;
        Integer horas = null;
        Duration dur = c.getDuracao();
        if (dur != null) {
            long h = dur.toHours();
            horas = (int) h;
        }
        return CursoDTO.builder()
                .id(c.getId())
                .titulo(c.getTitulo())
                .descricao(c.getDescricao())
                .duracaoHoras(horas)
                .nivelDificuldade(c.getNivelDificuldade())
                .preco(c.getPreco())
                .status(c.getStatus() != null ? c.getStatus().name() : null)
                .build();
    }
}
