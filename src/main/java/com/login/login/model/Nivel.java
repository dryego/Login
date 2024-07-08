package com.login.login.model;

import com.login.login.enums.NivelEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "niveis")
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NivelEnum nome;

    public Nivel(NivelEnum nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public NivelEnum getNome() {
        return nome;
    }

    public void setNome(NivelEnum nome) {
        this.nome = nome;
    }
}
