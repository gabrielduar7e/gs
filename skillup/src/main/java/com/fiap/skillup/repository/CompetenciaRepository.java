package com.fiap.skillup.repository;

import com.fiap.skillup.model.Competencia;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetenciaRepository extends BaseRepository<Competencia> {
    @Query("SELECT c FROM Competencia c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Competencia> buscarPorTermo(@Param("termo") String termo);
    
    List<Competencia> findByCategoria(Competencia.CategoriaCompetencia categoria);
    
    @Query("SELECT c FROM Competencia c JOIN c.usuarios uc WHERE uc.usuario.id = :usuarioId")
    List<Competencia> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
