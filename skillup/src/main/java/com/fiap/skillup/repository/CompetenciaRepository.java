package com.fiap.skillup.repository;

import com.fiap.skillup.model.Competencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface CompetenciaRepository extends BaseRepository<Competencia> {
    @Query("SELECT c FROM Competencia c WHERE c.active = true AND LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Competencia> buscarPorTermo(@Param("termo") String termo, Pageable pageable);
    
    Page<Competencia> findByCategoriaAndActiveTrue(Competencia.CategoriaCompetencia categoria, Pageable pageable);
    
    @Query("SELECT c FROM Competencia c JOIN c.usuarios uc WHERE c.active = true AND uc.usuario.id = :usuarioId")
    Page<Competencia> findByUsuarioId(@Param("usuarioId") Long usuarioId, Pageable pageable);

    Page<Competencia> findAllByActiveTrue(Pageable pageable);
}
