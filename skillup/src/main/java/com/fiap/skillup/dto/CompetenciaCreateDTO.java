package com.fiap.skillup.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaCreateDTO {
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode ter mais que 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição não pode ter mais que 500 caracteres")
    private String descricao;

    @NotBlank(message = "Categoria é obrigatória")
    private String categoria; // valores do enum CategoriaCompetencia
}
