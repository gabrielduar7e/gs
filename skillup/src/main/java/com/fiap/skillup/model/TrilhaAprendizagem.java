package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trilhas_aprendizagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrilhaAprendizagem extends BaseEntity {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título não pode ter mais que 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "Descrição não pode ter mais que 1000 caracteres")
    private String descricao;

    @NotNull(message = "Duração estimada é obrigatória")
    private Integer duracaoEstimada; // em horas

    @Min(value = 1, message = "Nível de dificuldade deve ser no mínimo 1")
    @Max(value = 5, message = "Nível de dificuldade deve ser no máximo 5")
    private Integer nivelDificuldade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTrilha status;

    @ManyToMany
    @JoinTable(
        name = "trilha_competencias_requeridas",
        joinColumns = @JoinColumn(name = "trilha_id"),
        inverseJoinColumns = @JoinColumn(name = "competencia_id")
    )
    @Builder.Default
    private Set<Competencia> competenciasRequeridas = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "trilha_cursos",
        joinColumns = @JoinColumn(name = "trilha_id"),
        inverseJoinColumns = @JoinColumn(name = "curso_id")
    )
    @Builder.Default
    private Set<Curso> cursos = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "trilha", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<ProgressoTrilha> progressos = new HashSet<>();

    public enum StatusTrilha {
        RASCUNHO("Rascunho"),
        ATIVA("Ativa"),
        CONCLUIDA("Concluída"),
        ARQUIVADA("Arquivada");

        private final String descricao;

        StatusTrilha(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
