package com.fiap.skillup.controller;

import com.fiap.skillup.dto.CompetenciaCreateDTO;
import com.fiap.skillup.dto.CompetenciaDTO;
import com.fiap.skillup.service.CompetenciaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/competencias")
public class CompetenciaController {

    private final CompetenciaService competenciaService;

    public CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<CompetenciaDTO>> listar(
            @RequestParam(value = "termo", required = false) String termo,
            @RequestParam(value = "categoria", required = false) String categoria,
            Pageable pageable) {
        return ResponseEntity.ok(competenciaService.listar(termo, categoria, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetenciaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(competenciaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CompetenciaDTO> criar(@Valid @RequestBody CompetenciaCreateDTO dto,
                                                UriComponentsBuilder uriBuilder) {
        CompetenciaDTO criado = competenciaService.criar(dto);
        return ResponseEntity
                .created(uriBuilder.path("/competencias/{id}").buildAndExpand(criado.getId()).toUri())
                .body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompetenciaDTO> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody CompetenciaCreateDTO dto) {
        return ResponseEntity.ok(competenciaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        competenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
