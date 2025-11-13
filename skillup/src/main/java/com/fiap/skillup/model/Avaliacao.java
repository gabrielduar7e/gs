package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao extends BaseEntity {

    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota deve ser no mínimo 1")
    @Max(value = 5, message = "Nota deve ser no máximo 5")
    private Integer nota;

    @Size(max = 1000, message = "Comentário não pode ter mais que 1000 caracteres")
    private String comentario;

    @Column(name = "data_avaliacao", nullable = false, updatable = false)
    private LocalDateTime dataAvaliacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @PrePersist
    protected void onPrePersist() {
        this.dataAvaliacao = LocalDateTime.now();
    }
}
