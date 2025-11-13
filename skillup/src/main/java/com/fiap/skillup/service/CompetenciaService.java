package com.fiap.skillup.service;

import com.fiap.skillup.dto.CompetenciaCreateDTO;
import com.fiap.skillup.dto.CompetenciaDTO;
import com.fiap.skillup.mapper.CompetenciaMapper;
import com.fiap.skillup.model.Competencia;
import com.fiap.skillup.repository.CompetenciaRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class CompetenciaService {

    private final CompetenciaRepository competenciaRepository;

    public CompetenciaService(CompetenciaRepository competenciaRepository) {
        this.competenciaRepository = competenciaRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "competencias", key = "T(String).format('%s|%s|%s|%s', #termo, #categoria, #pageable.pageNumber, #pageable.pageSize)")
    public Page<CompetenciaDTO> listar(String termo, String categoria, Pageable pageable) {
        Page<Competencia> page;
        if (termo != null && !termo.isBlank()) {
            page = competenciaRepository.buscarPorTermo(termo, pageable);
        } else if (categoria != null && !categoria.isBlank()) {
            Competencia.CategoriaCompetencia cat = Competencia.CategoriaCompetencia.fromString(categoria);
            page = competenciaRepository.findByCategoriaAndActiveTrue(cat, pageable);
        } else {
            page = competenciaRepository.findAllByActiveTrue(pageable);
        }
        return page.map(CompetenciaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "competencia", key = "#id")
    public CompetenciaDTO buscarPorId(Long id) {
        Competencia c = competenciaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Competência não encontrada"));
        return CompetenciaMapper.toDTO(c);
    }

    @CacheEvict(value = {"competencias"}, allEntries = true)
    public CompetenciaDTO criar(CompetenciaCreateDTO dto) {
        Competencia c = Competencia.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .categoria(Competencia.CategoriaCompetencia.fromString(dto.getCategoria()))
                .build();
        c = competenciaRepository.save(c);
        return CompetenciaMapper.toDTO(c);
    }

    @CacheEvict(value = {"competencias", "competencia"}, allEntries = true)
    public CompetenciaDTO atualizar(Long id, CompetenciaCreateDTO dto) {
        Competencia c = competenciaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Competência não encontrada"));
        c.setNome(dto.getNome());
        c.setDescricao(dto.getDescricao());
        if (dto.getCategoria() != null && !dto.getCategoria().isBlank()) {
            c.setCategoria(Competencia.CategoriaCompetencia.fromString(dto.getCategoria()));
        }
        c = competenciaRepository.save(c);
        return CompetenciaMapper.toDTO(c);
    }

    @CacheEvict(value = {"competencias", "competencia"}, allEntries = true)
    public void deletar(Long id) {
        Competencia c = competenciaRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Competência não encontrada"));
        c.setActive(false);
        competenciaRepository.save(c);
    }
}
