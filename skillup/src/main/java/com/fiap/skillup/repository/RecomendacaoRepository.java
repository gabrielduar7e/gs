package com.fiap.skillup.repository;

import com.fiap.skillup.model.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Long> {
    List<Recomendacao> findByUsuarioIdOrderByCreatedAtDesc(Long usuarioId);
}
