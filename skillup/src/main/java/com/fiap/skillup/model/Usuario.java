package com.fiap.skillup.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entidade que representa um usuário do sistema.
 * Implementa UserDetails para integração com Spring Security.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
toString
public class Usuario extends EntidadeBase implements UserDetails {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome não pode ter mais que 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    @Column(nullable = false)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "usuario_perfis", 
        joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "perfil")
    @Builder.Default
    private Set<Integer> perfis = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<UsuarioCompetencia> competencias = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<TrilhaAprendizagem> trilhasAprendizagem = new HashSet<>();

    /**
     * Retorna as autoridades (roles) do usuário para autenticação
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(Perfil::paraEnum)
                .map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao()))
                .collect(Collectors.toList());
    }

    /**
     * Adiciona um perfil ao usuário
     */
    public void adicionarPerfil(Perfil perfil) {
        perfis.add(perfil.getCodigo());
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isAtivo();
    }

    /**
     * Enum que representa os perfis de usuário no sistema
     */
    public enum Perfil {
        ADMIN(1, "ROLE_ADMIN"),
        USUARIO(2, "ROLE_USUARIO");

        private final int codigo;
        private final String descricao;

        Perfil(int codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public int getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

        /**
         * Converte um código numérico para o enum correspondente
         */
        public static Perfil paraEnum(Integer codigo) {
            if (codigo == null) {
                return null;
            }
            for (Perfil perfil : Perfil.values()) {
                if (codigo.equals(perfil.getCodigo())) {
                    return perfil;
                }
            }
            throw new IllegalArgumentException("Código de perfil inválido: " + codigo);
        }
    }
}
