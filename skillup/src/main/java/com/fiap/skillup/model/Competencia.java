package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa uma competência no sistema.
 * Pode ser associada a usuários, cursos e trilhas de aprendizagem.
 */
@Entity
@Table(name = "competencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competencia extends BaseEntity {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode ter mais que 100 caracteres")
    @Column(unique = true, nullable = false)
    private String nome;

    @Size(max = 500, message = "Descrição não pode ter mais que 500 caracteres")
    @Column(length = 500)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 20)
    private CategoriaCompetencia categoria;

    @OneToMany(mappedBy = "competencia", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UsuarioCompetencia> usuarios = new HashSet<>();

    @ManyToMany(mappedBy = "competencias")
    @Builder.Default
    private Set<Curso> cursos = new HashSet<>();

    @ManyToMany(mappedBy = "competenciasRequeridas")
    @Builder.Default
    private Set<TrilhaAprendizagem> trilhasRequeridas = new HashSet<>();

    /**
     * Enum que representa as categorias de competências disponíveis no sistema
     */
    public enum CategoriaCompetencia {
        TECNICA("Técnica"),
        COMPORTAMENTAL("Comportamental"),
        GESTAO("Gestão"),
        IDIOMAS("Idiomas"),
        OUTRA("Outra");

        private final String descricao;

        CategoriaCompetencia(String descricao) {
            this.descricao = descricao;
        }

        /**
         * @return Descrição amigável da categoria
         */
        public String getDescricao() {
            return descricao;
        }

        /**
         * Converte uma string para o enum correspondente
         */
        public static CategoriaCompetencia fromString(String text) {
            if (text != null) {
                for (CategoriaCompetencia categoria : CategoriaCompetencia.values()) {
                    if (categoria.name().equalsIgnoreCase(text)) {
                        return categoria;
                    }
                }
            }
            return OUTRA; // Valor padrão
        }
    }
}
