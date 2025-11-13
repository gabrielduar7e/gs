package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresso_trilhas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressoTrilha extends BaseEntity {

    @NotNull(message = "Percentual de conclusão é obrigatório")
    @Min(value = 0, message = "Percentual de conclusão não pode ser menor que 0")
    @Max(value = 100, message = "Percentual de conclusão não pode ser maior que 100")
    private Integer percentualConclusao;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_conclusao")
    private LocalDateTime dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trilha_id", nullable = false)
    private TrilhaAprendizagem trilha;

    @PrePersist
    protected void onPrePersist() {
        if (this.dataInicio == null) {
            this.dataInicio = LocalDateTime.now();
        }
        if (this.percentualConclusao == null) {
            this.percentualConclusao = 0;
        }
    }
}
