package com.fiap.skillup.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErro {
    private OffsetDateTime timestamp;
    private int status;
    private String erro;
    private String mensagem;
    private String caminho;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> detalhes;
}
