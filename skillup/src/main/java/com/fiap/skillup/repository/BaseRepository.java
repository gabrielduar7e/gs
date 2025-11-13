package com.fiap.skillup.repositorio;

import com.fiap.skillup.modelo.EntidadeBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

/**
 * Interface base para todos os repositórios do sistema.
 * Fornece operações comuns de CRUD e consulta.
 *
 * @param <T> Tipo da entidade gerenciada pelo repositório
 */
@NoRepositoryBean
public interface RepositorioBase<T extends EntidadeBase> 
    extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    
    /**
     * Busca todas as entidades ativas
     * @return Lista de entidades ativas
     */
    List<T> findAllByAtivoTrue();
    
    /**
     * Busca uma entidade ativa por ID
     * @param id ID da entidade
     * @return Entidade encontrada, se existir e estiver ativa
     */
    Optional<T> findByIdAndAtivoTrue(Long id);
}
