package com.fiap.skillup.controller;

import com.fiap.skillup.dto.UsuarioCreateDTO;
import com.fiap.skillup.dto.UsuarioDTO;
import com.fiap.skillup.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<UsuarioDTO>> listar(
            @RequestParam(value = "nome", required = false) String nome,
            Pageable pageable) {
        return ResponseEntity.ok(usuarioService.listar(nome, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto,
                                            UriComponentsBuilder uriBuilder) {
        UsuarioDTO criado = usuarioService.criar(dto);
        return ResponseEntity
                .created(uriBuilder.path("/usuarios/{id}").buildAndExpand(criado.getId()).toUri())
                .body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody UsuarioCreateDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
