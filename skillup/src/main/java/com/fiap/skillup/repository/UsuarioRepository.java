package com.fiap.skillup.repository;

import com.fiap.skillup.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM Usuario u JOIN u.perfis p WHERE p = :perfil")
    List<Usuario> findByPerfil(@Param("perfil") Integer perfil);
}
