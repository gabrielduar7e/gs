package com.fiap.skillup.repositorio;

import com.fiap.skillup.modelo.Competencia;
import com.fiap.skillup.modelo.Competencia.CategoriaCompetencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com a entidade Competência.
 * Fornece métodos para busca e manipulação de competências.
 */
@Repository
public interface CompetenciaRepositorio extends RepositorioBase<Competencia> {
    
    /**
     * Busca competências que contenham o termo no nome, ignorando maiúsculas e minúsculas.
     * 
     * @param termo Termo de busca
     * @return Lista de competências que correspondem ao termo
     */
    @Query("SELECT c FROM Competencia c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Competencia> buscarPorTermo(@Param("termo") String termo);
    
    /**
     * Busca competências por categoria.
     * 
     * @param categoria Categoria das competências a serem buscadas
     * @return Lista de competências da categoria informada
     */
    List<Competencia> findByCategoria(CategoriaCompetencia categoria);
    
    /**
     * Busca competências associadas a um usuário específico.
     * 
     * @param usuarioId ID do usuário
     * @return Lista de competências associadas ao usuário
     */
    @Query("SELECT c FROM Competencia c JOIN c.usuarios uc WHERE uc.usuario.id = :usuarioId")
    List<Competencia> buscarPorUsuarioId(@Param("usuarioId") Long usuarioId);
    
    /**
     * Verifica se existe uma competência com o nome informado, ignorando maiúsculas e minúsculas.
     * 
     * @param nome Nome da competência a ser verificada
     * @return true se existir uma competência com o nome, false caso contrário
     */
    boolean existsByNomeIgnoreCase(String nome);
    
    /**
     * Busca uma competência pelo nome, ignorando maiúsculas e minúsculas.
     * 
     * @param nome Nome da competência a ser buscada
     * @return Optional contendo a competência, se encontrada
     */
    Optional<Competencia> findByNomeIgnoreCase(String nome);
}
