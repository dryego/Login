package com.login.login.dtos.usuario;

import com.login.login.enums.NivelEnum;

public record CriarUsuarioDTO(String nome, String email, String senha, NivelEnum nivel) {
}
