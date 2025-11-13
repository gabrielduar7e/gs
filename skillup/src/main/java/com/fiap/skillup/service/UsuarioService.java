package com.fiap.skillup.service;

import com.fiap.skillup.dto.UsuarioCreateDTO;
import com.fiap.skillup.dto.UsuarioDTO;
import com.fiap.skillup.mapper.UsuarioMapper;
import com.fiap.skillup.model.Usuario;
import com.fiap.skillup.repository.UsuarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Cacheable(value = "usuarios", key = "T(String).format('%s|%s|%s', #nome, #pageable.pageNumber, #pageable.pageSize)")
    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listar(String nome, Pageable pageable) {
        Page<Usuario> page;
        if (nome != null && !nome.isBlank()) {
            page = usuarioRepository.findByNomeContainingIgnoreCaseAndActiveTrue(nome, pageable);
        } else {
            page = usuarioRepository.findAllByActiveTrue(pageable);
        }
        return page.map(UsuarioMapper::toDTO);
    }

    @Cacheable(value = "usuario", key = "#id")
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
        return UsuarioMapper.toDTO(usuario);
    }

    @CacheEvict(value = {"usuarios"}, allEntries = true)
    public UsuarioDTO criar(UsuarioCreateDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(passwordEncoder.encode(dto.getSenha()))
                .perfis(dto.getPerfis())
                .build();
        usuario = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    @CacheEvict(value = {"usuarios", "usuario"}, allEntries = true)
    public UsuarioDTO atualizar(Long id, UsuarioCreateDTO dto) {
        Usuario usuario = usuarioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        if (dto.getPerfis() != null) {
            usuario.setPerfis(dto.getPerfis());
        }
        usuario = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(usuario);
    }

    @CacheEvict(value = {"usuarios", "usuario"}, allEntries = true)
    public void deletar(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Usuário não encontrado"));
        usuario.setActive(false);
        usuarioRepository.save(usuario);
    }
}
