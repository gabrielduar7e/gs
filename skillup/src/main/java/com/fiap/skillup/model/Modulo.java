package com.fiap.skillup.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modulos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modulo extends BaseEntity {

    @NotBlank(message = "Título é obrigatório")
    @Size(max = 200, message = "Título não pode ter mais que 200 caracteres")
    private String titulo;

    @Size(max = 1000, message = "Descrição não pode ter mais que 1000 caracteres")
    private String descricao;

    @NotNull(message = "Ordem é obrigatória")
    private Integer ordem;

    @NotNull(message = "Duração é obrigatória")
    private Duration duracao; // em minutos

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    @Builder.Default
    private List<Aula> aulas = new ArrayList<>();
}
