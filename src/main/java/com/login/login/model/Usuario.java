package com.login.login.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "O campo nome não pode ser nulo.")
    private String nome;
    @Email(message = "Email deve ser válido.")
    private String email;
    @NotNull(message = "Senha nao pode ser nula.")
    private String senha;
    @NotNull(message = "Indique um tipo de usuario.")
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "nivel_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "nivel_id"))
    private List<Nivel> nivel;

    public Usuario(String nome, String email, String senha,List<Nivel> nivel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nivel = nivel;
    }

    public Usuario() {}

    public Long getId() {
        return id;
    }

    public @NotNull(message = "O campo nome não pode ser nulo.") String getNome() {
        return nome;
    }

    public void setNome(@NotNull(message = "O campo nome não pode ser nulo.") String nome) {
        this.nome = nome;
    }

    public @Email(message = "Email deve ser válido.") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Email deve ser válido.") String email) {
        this.email = email;
    }

    public @Size(min = 6, message = "Senha nao pode ser menor que 6 digitos.", max = 12) String getSenha() {
        return senha;
    }

    public void setSenha(@Size(min = 6, message = "Senha nao pode ser menor que 6 digitos.", max = 12) String senha) {
        this.senha = senha;
    }

    public @NotNull(message = "Indique um tipo de usuario.") List<Nivel> getNivel() {
        return nivel;
    }

    public void setNivel(@NotNull(message = "Indique um tipo de usuario.") List<Nivel> nivel) {
        this.nivel = nivel;
    }
}