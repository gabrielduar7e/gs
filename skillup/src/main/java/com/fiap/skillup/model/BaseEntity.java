package com.fiap.skillup.modelo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Classe base para todas as entidades do sistema.
 * Contém campos comuns como ID, datas de criação/atualização e status ativo.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class EntidadeBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gerador_padrao")
    private Long id;
    
    @CreationTimestamp
    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;
    
    @UpdateTimestamp
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;
    
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
    
    /**
     * Método executado antes de persistir uma nova entidade.
     * Define as datas de criação e atualização.
     */
    @PrePersist
    protected void aoCriar() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    /**
     * Método executado antes de atualizar uma entidade.
     * Atualiza a data de atualização.
     */
    @PreUpdate
    protected void aoAtualizar() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
