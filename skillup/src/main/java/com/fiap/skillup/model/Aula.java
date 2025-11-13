package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;

@Entity
@Table(name = "aulas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aula extends BaseEntity {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título não pode ter mais que 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "Descrição não pode ter mais que 1000 caracteres")
    private String descricao;

    @NotNull(message = "Ordem é obrigatória")
    private Integer ordem;

    @NotNull(message = "Duração é obrigatória")
    private Duration duracao; // em minutos

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAula tipo;

    @Size(max = 1000, message = "URL do conteúdo não pode ter mais que 1000 caracteres")
    private String urlConteudo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private Modulo modulo;

    public enum TipoAula {
        VIDEO("Vídeo"),
        TEXTO("Texto"),
        QUIZ("Quiz"),
        ATIVIDADE_PRATICA("Atividade Prática"),
        AO_VIVO("Aula ao Vivo");

        private final String descricao;

        TipoAula(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }
}
