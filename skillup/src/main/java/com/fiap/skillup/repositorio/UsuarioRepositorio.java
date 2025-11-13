package com.fiap.skillup.repositorio;

import com.fiap.skillup.modelo.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para operações com a entidade Usuário.
 * Fornece métodos para busca e manipulação de usuários.
 */
@Repository
public interface UsuarioRepositorio extends RepositorioBase<Usuario> {
    
    /**
     * Busca um usuário pelo email.
     * 
     * @param email Email do usuário a ser buscado
     * @return Optional contendo o usuário, se encontrado
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica se existe um usuário com o email informado.
     * 
     * @param email Email a ser verificado
     * @return true se existir um usuário com o email, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca usuários por perfil.
     * 
     * @param perfil Código do perfil a ser buscado
     * @return Lista de usuários com o perfil informado
     */
    @Query("SELECT u FROM Usuario u JOIN u.perfis p WHERE p = :perfil")
    List<Usuario> buscarPorPerfil(@Param("perfil") Integer perfil);
    
    /**
     * Busca usuários pelo nome, ignorando maiúsculas e minúsculas.
     * 
     * @param nome Nome ou parte do nome a ser buscado
     * @return Lista de usuários cujo nome contenha o termo informado
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> buscarPorNome(@Param("nome") String nome);
    
    /**
     * Busca usuários ativos ordenados por nome.
     * 
     * @return Lista de usuários ativos ordenados por nome
     */
    List<Usuario> findAllByAtivoTrueOrderByNomeAsc();
}
