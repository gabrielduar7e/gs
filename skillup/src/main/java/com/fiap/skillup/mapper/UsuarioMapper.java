package com.fiap.skillup.mapper;

import com.fiap.skillup.dto.UsuarioDTO;
import com.fiap.skillup.model.Usuario;

public final class UsuarioMapper {
    private UsuarioMapper() {}

    public static UsuarioDTO toDTO(Usuario u) {
        if (u == null) return null;
        return UsuarioDTO.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .perfis(u.getPerfis())
                .active(u.isActive())
                .build();
    }
}
