package com.fiap.skillup.service;

import com.fiap.skillup.dto.RecomendacaoDTO;
import com.fiap.skillup.model.Recomendacao;
import com.fiap.skillup.repository.RecomendacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacaoStoreService {

    private final RecomendacaoRepository repository;

    public void salvar(Long usuarioId, List<RecomendacaoDTO> lista) {
        List<Recomendacao> entidades = lista.stream().map(d ->
                Recomendacao.builder()
                        .usuarioId(usuarioId)
                        .titulo(d.getTitulo())
                        .descricao(d.getDescricao())
                        .score(d.getScore())
                        .build()
        ).collect(Collectors.toList());
        repository.saveAll(entidades);
    }

    public List<RecomendacaoDTO> listarHistorico(Long usuarioId) {
        return repository.findByUsuarioIdOrderByCreatedAtDesc(usuarioId)
                .stream()
                .map(r -> RecomendacaoDTO.builder()
                        .titulo(r.getTitulo())
                        .descricao(r.getDescricao())
                        .score(r.getScore())
                        .build())
                .collect(Collectors.toList());
    }
}
