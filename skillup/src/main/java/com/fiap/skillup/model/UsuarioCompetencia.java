package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "usuario_competencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UsuarioCompetencia.UsuarioCompetenciaId.class)
public class UsuarioCompetencia {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private Competencia competencia;

    @NotNull(message = "Nível de proficiência é obrigatório")
    @Min(value = 1, message = "Nível de proficiência deve ser no mínimo 1")
    @Max(value = 5, message = "Nível de proficiência deve ser no máximo 5")
    @Column(nullable = false)
    private Integer nivelProficiencia;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UsuarioCompetenciaId implements Serializable {
        private Long usuario;
        private Long competencia;
    }
}
