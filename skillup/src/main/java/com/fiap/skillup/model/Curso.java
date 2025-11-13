package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cursos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Curso extends BaseEntity {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título não pode ter mais que 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "Descrição não pode ter mais que 1000 caracteres")
    private String descricao;

    @NotNull(message = "Duração é obrigatória")
    private Duration duracao; // em minutos

    @Min(value = 0, message = "Carga horária não pode ser negativa")
    private Integer cargaHoraria; // em horas

    @Min(value = 0, message = "Nível não pode ser negativo")
    private Integer nivelDificuldade; // 1 a 5

    @DecimalMin(value = "0.0", message = "Avaliação não pode ser negativa")
    @DecimalMax(value = "5.0", message = "Avaliação máxima é 5.0")
    private Double mediaAvaliacao;

    @Min(value = 0, message = "Número de avaliações não pode ser negativo")
    private Integer totalAvaliacoes;

    @DecimalMin(value = "0.0", message = "Preço não pode ser negativo")
    private BigDecimal preco;

    @Size(max = 255, message = "URL da imagem não pode ter mais que 255 caracteres")
    private String urlImagem;

    @Size(max = 255, message = "URL do vídeo não pode ter mais que 255 caracteres")
    private String urlVideo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCurso status;

    @ManyToMany
    @JoinTable(
        name = "curso_competencias",
        joinColumns = @JoinColumn(name = "curso_id"),
        inverseJoinColumns = @JoinColumn(name = "competencia_id")
    )
    @Builder.Default
    private Set<Competencia> competencias = new HashSet<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Modulo> modulos = new HashSet<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Avaliacao> avaliacoes = new HashSet<>();

    @ManyToMany(mappedBy = "cursos")
    @Builder.Default
    private Set<TrilhaAprendizagem> trilhas = new HashSet<>();

    public enum StatusCurso {
        RASCUNHO("Rascunho"),
        PUBLICADO("Publicado"),
        ARQUIVADO("Arquivado"),
        EM_REVISAO("Em Revisão");

        private final String descricao;

        StatusCurso(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
